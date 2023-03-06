package com.towelove.system.service.mail;


import com.google.common.annotations.VisibleForTesting;
import com.towelove.system.domain.PageResult;
import com.towelove.system.domain.mail.MailTemplateDO;
import com.towelove.system.mq.producer.mail.MailProducer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * 邮箱模版 Service 实现类
 *
 * @author wangjingyi
 * @since 2022-03-21
 */
@Service
@Validated
@Slf4j
public class MailTemplateServiceImpl implements MailTemplateService {

    /**
     * 正则表达式，匹配 {} 中的变量
     */
    private static final Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");



    @Resource
    private MailProducer mailProducer;

    /**
     * 邮件模板缓存
     * key：邮件模版标识 MailTemplateDO getCode
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Map<String, MailTemplateDO> mailTemplateCache;

    @Override
    @PostConstruct
    public void initLocalCache() {

    }

    @Override
    public void deleteMailTemplate(Long id) {

    }

    @Override
    public MailTemplateDO getMailTemplate(Long id) {
        return null;
    }

    @Override
    public List<MailTemplateDO> getMailTemplateList() {
        return null;
    }

    @Override
    public MailTemplateDO getMailTemplateByCodeFromCache(String code) {
        return null;
    }

    @Override
    public String formatMailTemplateContent(String content, Map<String, Object> params) {
        return null;
    }

    @Override
    public long countByAccountId(Long accountId) {
        return 0;
    }


}
