package com.towelove.msg.task.mq.producer;

import com.towelove.common.core.constant.MessageConstant;
import com.towelove.common.core.domain.MailSendMessage;
import com.towelove.common.mq.core.bus.AbstractBusProducer;
import com.towelove.msg.task.domain.MailMsg;
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
public class MailMessageProducer extends AbstractBusProducer {
    @Resource
    private StreamBridge streamBridge;

    /**
     * 发送 {@link MailMsg} 消息给指定用户
     *
     */
    public void sendMailMessage(MailMsg mail) {
        log.info("接收到定时任务消息，并且准备发送给MQ：{}",mail);
        streamBridge.send(MessageConstant.TASK_MESSAGE_OUTPUT,
                mail);
        log.info("消息发送给MQ成功。。。");
    }
}
