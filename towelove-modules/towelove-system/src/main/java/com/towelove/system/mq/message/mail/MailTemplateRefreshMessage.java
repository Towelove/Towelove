package com.towelove.system.mq.message.mail;

import lombok.*;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 邮箱模板的数据刷新 Message
 *
 * @author: 张锦标
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class MailTemplateRefreshMessage extends RemoteApplicationEvent {

    public MailTemplateRefreshMessage() {
    }

    public MailTemplateRefreshMessage(Object source, String originService, String destinationService) {
        super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
    }

}
