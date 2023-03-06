package com.towelove.system.service.mail;


import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailException;
import cn.hutool.extra.mail.MailUtil;
import com.google.common.annotations.VisibleForTesting;
import com.towelove.common.core.enums.CommonStatusEnum;
import com.towelove.system.convert.MailAccountConvert;
import com.towelove.system.domain.mail.MailAccount;
import com.towelove.system.domain.mail.MailTemplate;
import com.towelove.system.mq.message.mail.MailSendMessage;
import com.towelove.system.mq.producer.mail.MailProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 邮箱发送 Service 实现类
 *
 * @author: 张锦标
 * @since 2022-03-21
 */
@Service
@Validated
@Slf4j
public class MailSendServiceImpl implements MailSendService {

    @Resource
    private MailAccountService mailAccountService;
    @Resource
    private MailTemplateService mailTemplateService;

    @Resource
    private MailLogService mailLogService;
    @Resource
    private MailProducer mailProducer;


    @Override
    public Long sendSingleMailToAdmin(String mail, Long userId, String templateCode,
                                      Map<String, Object> templateParams) {
        return null;
    }

    @Override
    public Long sendSingleMailToMember(String mail, Long userId,
                                       String templateCode, Map<String, Object> templateParams) {
        return null;
    }

    @Override
    public Long sendSingleMail(String mail, Long userId, Integer userType,
                               String templateCode, Map<String, Object> templateParams) {
        // 校验邮箱模版是否合法
        MailTemplate template = validateMailTemplate(templateCode);
        // 校验邮箱账号是否合法
        MailAccount account = validateMailAccount(template.getAccountId());
        // 校验邮箱是否存在
        mail = validateMail(mail);
        //检查模板参数是否合法
        validateTemplateParams(template, templateParams);

        // 创建发送日志。如果模板被禁用，则不发送短信，只记录日志
        Boolean isSend = CommonStatusEnum.ENABLE.getStatus().equals(template.getStatus());
        String content = mailTemplateService.
                formatMailTemplateContent(template.getContent(), templateParams);
        Long sendLogId = mailLogService.createMailLog(userId, userType, mail,
                account, template, content, templateParams, isSend);
        // 发送 MQ 消息，异步执行发送短信
        if (isSend) {
            mailProducer.sendMailSendMessage(sendLogId, mail, account.getId(),
                    template.getNickname(), template.getTitle(), content);
        }
        return sendLogId;
    }

    @Override
    public void doSendMail(MailSendMessage message) {
        // 1. 创建发送账号
        MailAccount account = validateMailAccount(message.getAccountId());
        cn.hutool.extra.mail.MailAccount mailAccount  =
                MailAccountConvert.INSTANCE.convert(account, message.getNickname());
        // 2. 发送邮件
        try {
            String messageId = MailUtil.send(mailAccount, message.getMail(),
                    message.getTitle(), message.getContent(),true);
            // 3. 更新结果（成功）
            mailLogService.updateMailSendResult(message.getLogId(), messageId, null);
        } catch (Exception e) {
            // 3. 更新结果（异常）
            mailLogService.updateMailSendResult(message.getLogId(), null, e);
        }

    }

    @VisibleForTesting
    MailTemplate validateMailTemplate(String templateCode) {
        // 获得邮件模板。考虑到效率，从缓存中获取
        MailTemplate template = mailTemplateService.getMailTemplateByCodeFromCache(templateCode);
        // 邮件模板不存在
        if (template == null) {
            throw new MailException("当前邮件模板不存在");
        }
        return template;
    }

    @VisibleForTesting
    MailAccount validateMailAccount(Long accountId) {
        // 获得邮箱账号。考虑到效率，从缓存中获取
        MailAccount account = mailAccountService.getMailAccountFromCache(accountId);
        // 邮箱账号不存在
        if (account == null) {
            throw new MailException("当前账户id:"+accountId+"不存在");
        }
        return account;
    }

    @VisibleForTesting
    String validateMail(String mail) {
        if (StrUtil.isEmpty(mail)) {
            throw new MailException("当前邮件:"+mail+"不存在");
        }
        return mail;
    }

    /**
     * 校验邮件参数是否确实
     *
     * @param template 邮箱模板
     * @param templateParams 参数列表
     */
    @VisibleForTesting
    void validateTemplateParams(MailTemplate template, Map<String, Object> templateParams) {
        template.getParams().forEach((key) -> {
            Object value = templateParams.get(key);
            if (value == null) {
                throw new MailException("当前参数键:"+key+"对应值不存在");
            }
        });
    }

}
