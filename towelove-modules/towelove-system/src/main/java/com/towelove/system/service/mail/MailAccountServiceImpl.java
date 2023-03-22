package com.towelove.system.service.mail;


import cn.hutool.extra.mail.MailException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.towelove.common.core.constant.RedisServiceConstants;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.common.redis.service.RedisService;
import com.towelove.system.convert.mail.MailAccountConvert;
import com.towelove.system.domain.mail.MailAccountDO;
import com.towelove.system.domain.mail.vo.account.MailAccountCreateReqVO;
import com.towelove.system.domain.mail.vo.account.MailAccountPageReqVO;
import com.towelove.system.domain.mail.vo.account.MailAccountUpdateReqVO;
import com.towelove.system.mapper.mail.MailAccountMapper;
import com.towelove.system.mq.producer.mail.MailProducer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.towelove.common.core.utils.CollectionUtils.convertMap;

/**
 * 邮箱账号 Service 实现类
 *
 * @author: 张锦标
 * @since 2023-03-11
 */
@Service
@Validated
@Slf4j
public class MailAccountServiceImpl implements MailAccountService {

    @Resource
    private MailAccountMapper mailAccountMapper;

    @Resource
    private MailTemplateService mailTemplateService;

    @Resource
    private MailProducer mailProducer;
    @Resource
    private RedisService redisService;
    /**
     * 邮箱账号缓存 创建的是本地缓存 使用的不是redis
     * 后期可以换为redis
     * key：邮箱账号编码 {@link MailAccountDO#getId()}
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Map<Long, MailAccountDO> mailAccountCache;

    @Autowired
    private RedisCache redisCache;
    @Override
    @PostConstruct //当前类构造后就会执行当前方法
    public void initLocalCache() {
        // 第一步：查询所有的数据（可以优化 不应该使用jvm内存来做缓存）
        List<MailAccountDO> accounts = mailAccountMapper.selectList();
        log.info("[initLocalCache][缓存邮箱账号，数量:{}]", accounts.size());
        // 第二步：构建缓存 也就是把所有的数据库里面查询出来的id放在我们的代码中
        mailAccountCache = convertMap(accounts, MailAccountDO::getId);
    }

    @Override
    public MailAccountDO getMailAccountFromCache(Long id) {
        return mailAccountCache.get(id);
    }

    @Override
    public Long createMailAccount(MailAccountCreateReqVO createReqVO) {
        // 插入
        MailAccountDO account = MailAccountConvert.INSTANCE.convert(createReqVO);
        mailAccountMapper.insert(account);

        // 发送刷新消息
        //mailProducer.sendMailAccountRefreshMessage();
        return account.getId();
    }
    public static final int WAIT_SIZE = 10;
    private static List<MailAccountDO> waitList = new ArrayList<>();
    //TODO 使用Write Behind方式来保证数据库与缓存的数据一致性
    private void writeBehindUpdateMailAccount(MailAccountUpdateReqVO updateReqVO){
        // 校验是否存在
        validateMailAccountExists(updateReqVO.getId());
        MailAccountDO updateObj = MailAccountConvert.INSTANCE.convert(updateReqVO);
        //先更新更新缓存
        redisService.setCacheObject(RedisServiceConstants.SYS_MAIL_ACCOUNT
                +updateReqVO.getId(),updateObj);
        //先把要更新的数据放到一个队列中 之后批量更新
        waitList.add(updateObj);
        //TODO 使用全局线程池去管理这个任务
        if (waitList.size() == WAIT_SIZE){
            new Thread(()->{
                //批量执行任务
                mailAccountMapper.updateBatch(waitList,WAIT_SIZE);
                //清空任务
                waitList.clear();
            }).start();
        }
    }

    @Override
    public void updateMailAccount(MailAccountUpdateReqVO updateReqVO) {
        // 校验是否存在
        validateMailAccountExists(updateReqVO.getId());

        // 更新
        MailAccountDO updateObj = MailAccountConvert.INSTANCE.convert(updateReqVO);
        mailAccountMapper.updateById(updateObj);
        // 发送刷新消息
        //mailProducer.sendMailAccountRefreshMessage();
        //更新缓存
        redisService.setCacheObject(RedisServiceConstants.SYS_MAIL_ACCOUNT+updateReqVO.getId(),updateObj);
    }

    @Override
    public MailAccountDO getMailAccount(Long id) {
        return mailAccountMapper.selectById(id);
    }

    @Override
    public PageResult<MailAccountDO> getMailAccountPage(MailAccountPageReqVO pageReqVO){
        return mailAccountMapper.selectPage(pageReqVO);
    }
    //TODO 一个人有没有可能它可以有多个邮箱。。。
    @Override
    public MailAccountDO getMailAccountByUserId(Long userId) {
        LambdaQueryWrapper<MailAccountDO> lqw =  new LambdaQueryWrapper();
        lqw.eq(MailAccountDO::getUserId,userId);
        return mailAccountMapper.selectOne(lqw);
    }

    @Override
    public List<MailAccountDO> getMailAccountList() {
        return mailAccountMapper.selectList();
    }

    @Override
    public void deleteMailAccount(Long id) {
        // 校验是否存在账号
        validateMailAccountExists(id);
        // 校验是否存在关联模版
        if (mailTemplateService.countByAccountId(id) > 0) {
            throw new MailException("无法删除，该邮箱账号还有邮件模板");
        }

        // 删除
        mailAccountMapper.deleteById(id);
        // 发送刷新消息
        //mailProducer.sendMailAccountRefreshMessage();
    }

    private void validateMailAccountExists(Long id) {
        if (mailAccountMapper.selectById(id) == null) {
            throw new MailException("邮箱账号不存在");
        }
    }
}
