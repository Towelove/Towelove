package com.towelove.system.service.mail;


import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.enums.MailSendStatusEnum;
import com.towelove.system.domain.mail.MailAccount;
import com.towelove.system.domain.mail.MailLog;
import com.towelove.system.domain.mail.MailTemplate;
import com.towelove.system.domain.mail.vo.MailLogPageReqVO;
import com.towelove.system.mapper.MailLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static cn.hutool.core.exceptions.ExceptionUtil.getRootCauseMessage;


/**
 * 邮件日志 Service 实现类
 *
 * @author: 张锦标
 * @since 2022-03-21
 */
@Service
@Validated
public class MailLogServiceImpl implements MailLogService {

    @Resource
    private MailLogMapper mailLogMapper;

    @Override
    public PageResult<MailLog> getMailLogPage(MailLogPageReqVO pageVO) {
        return mailLogMapper.selectPage(pageVO);
    }

    @Override
    public MailLog getMailLog(Long id) {
        return mailLogMapper.selectById(id);
    }

    @Override
    public Long createMailLog(Long userId, Integer userType, String toMail, MailAccount account,
                              MailTemplate template, String templateContent, Map<String, Object> templateParams,
                              Boolean isSend) {

        MailLog.MailLogBuilder logDOBuilder = MailLog.builder();
        // 根据是否要发送，设置状态
        logDOBuilder.sendStatus(Objects.equals(isSend, true) ? MailSendStatusEnum.INIT.getStatus()
                        : MailSendStatusEnum.IGNORE.getStatus())
                // 用户信息
                .userId(userId).userType(userType).toMail(toMail)
                .accountId(account.getId()).fromMail(account.getMail())
                // 模板相关字段
                .templateId(template.getId()).templateCode(template.getCode()).templateNickname(template.getNickname())
                .templateTitle(template.getTitle()).templateContent(templateContent).templateParams(templateParams);

        // 插入数据库
        MailLog logDO = logDOBuilder.build();
        mailLogMapper.insert(logDO);
        return logDO.getId();
    }



    @Override
    public void updateMailSendResult(Long logId, String messageId, Exception exception) {
        // 1. 成功
        if (exception == null) {
            mailLogMapper.updateById(new MailLog().setId(logId).setSendTime(new Date())
                    .setSendStatus(MailSendStatusEnum.SUCCESS
                            .getStatus()).setSendMessageId(messageId));
            return;
        }
        // 2. 失败
        mailLogMapper.updateById(new MailLog().setId(logId).setSendTime(new Date())
                .setSendStatus(MailSendStatusEnum.FAILURE.getStatus())
                .setSendException(getRootCauseMessage(exception)));

    }

}
