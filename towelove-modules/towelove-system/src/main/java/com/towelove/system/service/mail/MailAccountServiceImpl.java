package com.towelove.system.service.mail;


import cn.hutool.extra.mail.MailException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.system.convert.mail.MailAccountConvert;
import com.towelove.system.domain.mail.MailAccountDO;
import com.towelove.system.domain.mail.vo.account.MailAccountCreateReqVO;
import com.towelove.system.domain.mail.vo.account.MailAccountPageReqVO;
import com.towelove.system.domain.mail.vo.account.MailAccountUpdateReqVO;
import com.towelove.system.mapper.mail.MailAccountMapper;
import com.towelove.system.mq.producer.mail.MailProducer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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

    /**
     * 邮箱账号缓存 创建的是本地缓存 使用的不是redis
     * 后期可以换为redis
     * key：邮箱账号编码 {@link MailAccountDO#getId()}
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Map<Long, MailAccountDO> mailAccountCache;

    @Override
    @PostConstruct
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

    @Override
    public void updateMailAccount(MailAccountUpdateReqVO updateReqVO) {
        // 校验是否存在
        validateMailAccountExists(updateReqVO.getId());

        // 更新
        MailAccountDO updateObj = MailAccountConvert.INSTANCE.convert(updateReqVO);
        mailAccountMapper.updateById(updateObj);
        // 发送刷新消息
        //mailProducer.sendMailAccountRefreshMessage();
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
