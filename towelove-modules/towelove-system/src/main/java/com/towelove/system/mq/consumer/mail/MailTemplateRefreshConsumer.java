package com.towelove.system.mq.consumer.mail;


import com.towelove.system.mq.message.mail.MailTemplateRefreshMessage;
import com.towelove.system.service.mail.MailTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 MailTemplateRefreshMessage 的消费者
 *
 * @author: 张锦标
 */
@Component
@Slf4j
public class MailTemplateRefreshConsumer {

    @Resource
    private MailTemplateService mailTemplateService;

    @EventListener
    public void onMessage(MailTemplateRefreshMessage message) {
        log.info("[onMessage][收到 Mail Template 刷新信息]");
        mailTemplateService.initLocalCache();
    }

}
