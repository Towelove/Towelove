package com.towelove.system.mq.consumer.mail;


import com.towelove.system.mq.message.mail.MailSendMessage;
import com.towelove.system.service.mail.MailSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.function.Consumer;

/**
 * 针对 MailSendMessage 的消费者
 * 代码中只要接收到MailSendMessage的消息对象
 * 就会自动执行accept中的方法
 * @author 张锦标
 * @date: 2023/3/7 20:10
 */
@Component
@Slf4j
public class MailSendConsumer implements Consumer<MailSendMessage> {

    @Resource
    private MailSendService mailSendService;

    /**
     * 定义一个消费实现，代码中接收到consumer对象，
     * 直接调用consumer.accept(T);方法就会调用自定义的消费实现方法。
     * @param message the input argument
     */
    @Override
    public void accept(MailSendMessage message) {
        log.info("[accept][消息内容({})]", message);
        mailSendService.doSendMail(message);
    }

}
