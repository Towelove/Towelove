package com.towelove.spring.cloud.mq.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2023/3/2 22:23
 * MyConsumer类
 */
@Component
public class MyConsumer {
    @StreamListener(Sink.INPUT)
    public void processMessage(String message){
        System.out.println("收到的消息："+message);
    }


}
