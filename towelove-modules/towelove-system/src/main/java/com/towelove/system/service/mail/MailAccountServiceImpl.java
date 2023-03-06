package com.towelove.system.service.mail;


import com.towelove.system.domain.PageResult;
import com.towelove.system.domain.mail.MailAccountDO;
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

/**
 * 邮箱账号 Service 实现类
 *
 * @author wangjingyi
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
     * 邮箱账号缓存
     * key：邮箱账号编码 {@link MailAccountDO#getId()}
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Map<Long, MailAccountDO> mailAccountCache;

    @Override
    @PostConstruct
    public void initLocalCache() {

    }

    @Override
    public MailAccountDO getMailAccountFromCache(Long id) {
        return mailAccountCache.get(id);
    }

    @Override
    public Long createMailAccount(MailAccountCreateReqVO createReqVO) {
        return null;
    }

    @Override
    public void updateMailAccount(MailAccountUpdateReqVO updateReqVO) {

    }

    @Override
    public void deleteMailAccount(Long id) {

    }

    private void validateMailAccountExists(Long id) {

    }

    @Override
    public MailAccountDO getMailAccount(Long id) {
        return mailAccountMapper.selectById(id);
    }

    @Override
    public PageResult<MailAccountDO> getMailAccountPage(MailAccountPageReqVO pageReqVO) {
        return null;
    }

    @Override
    public List<MailAccountDO> getMailAccountList() {
        return null;
    }

}
