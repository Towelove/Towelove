package com.towelove.system.service.mail;


import com.towelove.system.domain.mail.MailAccount;
import com.towelove.system.domain.mail.MailLog;
import com.towelove.system.domain.mail.MailTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Map;


/**
 * 邮件日志 Service 实现类
 *
 * @author: 张锦标
 * @since 2022-03-21
 */
@Service
@Validated
public class MailLogServiceImpl implements MailLogService {


    @Override
    public MailLog getMailLog(Long id) {
        return null;
    }

    @Override
    public Long createMailLog(Long userId, Integer userType, String toMail,
                              MailAccount account, MailTemplate template,
                              String templateContent, Map<String, Object> templateParams, Boolean isSend) {
        return null;
    }

    @Override
    public void updateMailSendResult(Long logId, String messageId, Exception exception) {

    }

}
