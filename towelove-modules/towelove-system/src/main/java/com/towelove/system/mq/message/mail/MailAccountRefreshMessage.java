package com.towelove.system.mq.message.mail;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 邮箱账号的数据刷新 Message
 * 毕竟我们的项目启动的时候只是吧所有的数据加载到
 * JVM的内存中，那么如果只执行一次 那么随着项目的运行
 * 就会发现后面有些新增加的数据就不存在了
 * 因此需要发送消息给MQ让其通知项目去刷新
 * 那么这就需要用到springcloudbus广播机制
 * 让所有的项目都去监听这个刷新的消息
 * @author: 张锦标
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MailAccountRefreshMessage extends RemoteApplicationEvent {

    public MailAccountRefreshMessage() {
    }

    public MailAccountRefreshMessage(Object source, String originService, String destinationService) {
        super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
    }

}
