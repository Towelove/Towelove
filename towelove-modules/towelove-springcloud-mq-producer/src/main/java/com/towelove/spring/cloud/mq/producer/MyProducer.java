package com.towelove.spring.cloud.mq.producer;

import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 张锦标
 * @date: 2023/3/2 21:57
 * MyProducer类
 */
@Component
public class MyProducer {
    @Resource
    private Source source;

    public void sendMessage(String msg){
        //封装消息头
        Map<String,Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS,"TagA");
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        //创建消息对象
        Message<String> message = MessageBuilder.createMessage(msg, messageHeaders);
        //发送消息
        source.output().send(message);
        System.out.println(message+"发送成功");
    }


}
