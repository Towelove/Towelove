package com.towelove.spring.cloud.mq.consumer.mail;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
public class MyConsumer {
    @StreamListener(Sink.INPUT)
    public void processMessage(String message){
        System.out.println("收到的消息："+message);
    }


}
