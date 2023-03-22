package com.towelove.rocketmq.producer.producer;

import com.towelove.common.core.constant.MessageConstant;
import com.towelove.common.core.domain.MailSendMessage;

import com.towelove.common.mq.core.bus.AbstractBusProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: 张锦标
 * @date: 2023/3/13 12:15
 * TaskMessageProducer类
 */
@Component
@Slf4j
public class TaskMessageProducer extends AbstractBusProducer {
    @Resource
    private StreamBridge streamBridge;

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
        streamBridge.send(MessageConstant.TASK_MESSAGE_OUTPUT, message);
    }
}
