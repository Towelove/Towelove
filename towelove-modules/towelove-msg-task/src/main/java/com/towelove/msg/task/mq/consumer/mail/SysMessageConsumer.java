package com.towelove.msg.task.mq.consumer.mail;



import com.towelove.common.core.domain.MailSendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author: 张锦标
 * @date: 2023/3/13 22:23
 * SysMesageConsumer类
 */
@Component
@Slf4j
public class SysMessageConsumer implements Consumer<MailSendMessage> {


    @Override
    public void accept(MailSendMessage mailSendMessage) {
        log.info("接收到系统消息: {}",mailSendMessage);
    }
}
