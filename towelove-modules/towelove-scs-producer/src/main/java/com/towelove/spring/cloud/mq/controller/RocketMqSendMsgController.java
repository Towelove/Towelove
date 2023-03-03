package com.towelove.spring.cloud.mq.controller;

import com.towelove.spring.cloud.mq.domain.BaseMessage;
import com.towelove.spring.cloud.mq.domain.MessageConstant;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.towelove.spring.cloud.mq.domain.MessageConstant.BROADCAST_MESSAGE_OUTPUT;
import static com.towelove.spring.cloud.mq.domain.MessageConstant.CLUSTER_MESSAGE_OUTPUT;

/**
 * @author: 张锦标
 * @date: 2023/3/2 20:23
 * SpringCloudStreamApplication类
 */
@RestController
public class RocketMqSendMsgController {

    @Autowired
    private StreamBridge streamBridge;

    @PostMapping(value = "/cluster")
    public void sendClusterMsg(@RequestParam("message") String message) {
        Message<BaseMessage<String>> msg = new GenericMessage<>(new BaseMessage<>(CLUSTER_MESSAGE_OUTPUT,"",message));
        boolean result = streamBridge.send(CLUSTER_MESSAGE_OUTPUT, msg);
        System.out.println(Thread.currentThread().getName() + " 消息集群发送: " + msg.getPayload().getData());
    }
    @PostMapping(value = "/broadcast")
    public void sendBroadcastMsg(@RequestParam("message") String message) {
        Message<BaseMessage<String>> msg = new GenericMessage<>(new BaseMessage<>(BROADCAST_MESSAGE_OUTPUT,"",message));
        boolean result = streamBridge.send(BROADCAST_MESSAGE_OUTPUT, msg);
        System.out.println(Thread.currentThread().getName() + " 消息广播发送: " + msg.getPayload().getData());
    }

    @PostMapping(value = "/delayed")
    public void sendDelayedMsg(@RequestParam("message") String message) {
        String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";

        for (int i = 0; i < 100; i++) {
            String key = "KEY" + i;
            Map<String, Object> headers = new HashMap<>();
            headers.put(MessageConst.PROPERTY_KEYS, key);
            headers.put(MessageConst.PROPERTY_ORIGIN_MESSAGE_ID, i);
            // 设置延时等级1~10
            headers.put(MessageConst.PROPERTY_DELAY_TIME_LEVEL, 4);
            BaseMessage<String> baseMessage = new BaseMessage<>(MessageConstant.DELAYED_MESSAGE_OUTPUT, message);
            baseMessage.setHeader(headers);
            Message<BaseMessage<String>> msg = new GenericMessage<>(baseMessage, headers);
            streamBridge.send(MessageConstant.DELAYED_MESSAGE_OUTPUT, msg);
            System.out.println(Thread.currentThread().getName() + " 延时消息: " + msg.getPayload().getData());
        }
    }
}