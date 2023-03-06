package com.towelove.system.service.mail;


import cn.hutool.extra.mail.MailException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.towelove.common.core.constant.HttpStatus;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.common.core.web.page.TableDataInfo;

import com.towelove.system.domain.mail.MailAccount;
import com.towelove.system.domain.mail.vo.MailAccountCreateReqVO;
import com.towelove.system.domain.mail.vo.MailAccountPageReqVO;
import com.towelove.system.domain.mail.vo.MailAccountUpdateReqVO;
import com.towelove.system.mapper.MailAccountMapper;
import com.towelove.system.mq.producer.mail.MailProducer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.towelove.common.core.utils.CollectionUtils.convertMap;

/**
 * 邮箱账号 Service 实现类
 *
 * @author: 张锦标
 * @since 2022-03-21
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
     * key：邮箱账号编码 {@link MailAccount#getId()}
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Map<Long, MailAccount> mailAccountCache;

    @Override
    @PostConstruct
    public void initLocalCache() {
        // 第一步：查询所有的数据（可以优化 不应该使用jvm内存来做缓存）
        List<MailAccount> accounts = mailAccountMapper.selectList();
        log.info("[initLocalCache][缓存邮箱账号，数量:{}]", accounts.size());
        // 第二步：构建缓存 也就是把所有的数据库里面查询出来的id放在我们的代码中
        mailAccountCache = convertMap(accounts, MailAccount::getId);
    }

    @Override
    public MailAccount getMailAccountFromCache(Long id) {
        return mailAccountCache.get(id);
    }

    @Override
    public Long createMailAccount(MailAccount account) {
        // 插入
        mailAccountMapper.insert(account);

        // 发送刷新消息
        mailProducer.sendMailAccountRefreshMessage();
        return account.getId();
    }

    @Override
    public void updateMailAccount(MailAccount account) {
        // 校验是否存在
        validateMailAccountExists(account.getId());

        // 更新
        mailAccountMapper.updateById(account);
        // 发送刷新消息
        mailProducer.sendMailAccountRefreshMessage();
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
        mailProducer.sendMailAccountRefreshMessage();
    }

    private void validateMailAccountExists(Long id) {
        if (mailAccountMapper.selectById(id) == null) {
            throw new MailException("邮箱账号不存在");
        }
    }

    @Override
    public MailAccount getMailAccount(Long id) {
        return mailAccountMapper.selectById(id);
    }

    @Override
    public PageResult<MailAccount> getMailAccountPage(MailAccountPageReqVO pageReqVO){
        return mailAccountMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MailAccount> getMailAccountList() {
        return mailAccountMapper.selectList();
    }

}
