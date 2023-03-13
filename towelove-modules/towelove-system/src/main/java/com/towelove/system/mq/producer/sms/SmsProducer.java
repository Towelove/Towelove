package com.towelove.system.mq.producer.sms;


import com.towelove.common.mq.core.bus.AbstractBusProducer;
import com.towelove.system.mq.message.sms.SmsSendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

/**
 * @author: 张锦标
 * @date: 2023/3/8 17:22
 * SmsProducer类
 */
@Slf4j
@Service
public class SmsProducer extends AbstractBusProducer {
    @Autowired
    private StreamBridge streamBridge;

    public void sendSms(SmsSendMessage message) {
        log.info("要发送的短信内容为: {}", message);
        streamBridge.send("sendSms-out-0", message);
    }
    public void sendSmsToAdmin(SmsSendMessage message) {
        log.info("要发送的短信内容为: {}", message);
        streamBridge.send("sendSmsToAdmin-out-0", message);
    }

    public void sendSmsToUser(Long userId,Long accountId) {
        log.info("要发送的短信内容为: {}", "userId:"+userId+"accountId:"+accountId);
        streamBridge.send("sendSmsToUser-out-0",  "userId:"+userId+"  accountId:"+accountId);
    }

}
