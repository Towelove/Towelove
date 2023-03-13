package com.towelove.spring.cloud.mq.consumer.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
public class RocketMqReceiveMsgController {

    @Autowired
    private StreamBridge streamBridge;

    /**
     * 函数式编辑接收消息
     */
    @Bean
    public Consumer<String> sendSysMsg() {
        return message -> {
            System.out.println("接收的Sys消息为：" + message);
        };
    }

    /**
     * 函数式编辑接收消息
     */
    @Bean
    public Consumer<String> sendTaskMsg() {
        return message -> {
            System.out.println("接收的Task消息为：" + message);
        };
    }


}