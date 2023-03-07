package com.towelove.system.mq.producer.mail;

import com.towelove.common.mq.core.bus.AbstractBusProducer;
import com.towelove.system.mq.message.mail.MailAccountRefreshMessage;
import com.towelove.system.mq.message.mail.MailSendMessage;
import com.towelove.system.mq.message.mail.MailTemplateRefreshMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class MailProducer extends AbstractBusProducer {

    @Resource
    private StreamBridge streamBridge;

    /**
     * 发送  消息
     */
    public void sendMailTemplateRefreshMessage() {
        publishEvent(
                new MailTemplateRefreshMessage(
                        this, getBusId(), selfDestinationService()));
    }

    /**
     * 发送消息
     */
    public void sendMailAccountRefreshMessage() {
        publishEvent(
                new MailAccountRefreshMessage(this, getBusId(), selfDestinationService()));
    }

    /**
     * 发送 {@link MailSendMessage} 消息
     *
     * @param sendLogId 发送日志编码
     * @param mail 接收邮件地址
     * @param accountId 邮件账号编号
     * @param nickname 邮件发件人
     * @param title 邮件标题
     * @param content 邮件内容
     */
    public void sendMailSendMessage(Long sendLogId, String mail, Long accountId,
                                    String nickname, String title, String content) {
        MailSendMessage message = new MailSendMessage()
                .setLogId(sendLogId).setMail(mail).setAccountId(accountId)
                .setNickname(nickname).setTitle(title).setContent(content);
        streamBridge.send("smsMail-out-0", message);
    }

}
