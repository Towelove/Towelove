package com.towelove.system.service.mail;


import com.towelove.system.domain.PageResult;
import com.towelove.system.domain.mail.MailAccountDO;
import com.towelove.system.domain.mail.MailLogDO;
import com.towelove.system.domain.mail.MailTemplateDO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;


/**
 * 邮件日志 Service 实现类
 *
 * @author wangjingyi
 * @since 2022-03-21
 */
@Service
@Validated
public class MailLogServiceImpl implements MailLogService {


    @Override
    public MailLogDO getMailLog(Long id) {
        return null;
    }

    @Override
    public Long createMailLog(Long userId, Integer userType, String toMail,
                              MailAccountDO account, MailTemplateDO template,
                              String templateContent, Map<String, Object> templateParams, Boolean isSend) {
        return null;
    }

    @Override
    public void updateMailSendResult(Long logId, String messageId, Exception exception) {

    }

}
