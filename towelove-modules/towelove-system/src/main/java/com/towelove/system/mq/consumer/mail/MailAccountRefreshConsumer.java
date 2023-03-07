package com.towelove.system.mq.consumer.mail;


import com.towelove.system.mq.message.mail.MailAccountRefreshMessage;
import com.towelove.system.service.mail.MailAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 MailAccountRefreshMessage 的消费者
 * 监听MailAccountRefreshMessage
 * @author: 张锦标
 */
@Component
@Slf4j
public class MailAccountRefreshConsumer {

    @Resource
    private MailAccountService mailAccountService;

    @EventListener
    public void onMessage(MailAccountRefreshMessage message) {
        log.info("[onMessage][收到 Mail Account 刷新信息]");
        mailAccountService.initLocalCache();
    }

}
