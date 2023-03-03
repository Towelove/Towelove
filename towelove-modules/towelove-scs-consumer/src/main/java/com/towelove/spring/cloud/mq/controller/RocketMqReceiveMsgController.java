package com.towelove.spring.cloud.mq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

/**
 * @author: 张锦标
 * @date: 2023/3/2 20:23
 * SpringCloudStreamApplication类
 */
@RestController
public class RocketMqReceiveMsgController {

    @Autowired
    private StreamBridge streamBridge;

    /**
     * 函数式编辑接收消息
     */
    @Bean
    public Consumer<String> cluster() {
        return message -> {
            System.out.println("接收的集群消息为：" + message);
        };
    }

    /**
     * 函数式编辑接收消息
     */
    @Bean
    public Consumer<String> broadcast() {
        return message -> {
            System.out.println("接收的广播消息为：" + message);
        };
    }

    /**
     * 函数式编辑接收消息
     */
    @Bean
    public Consumer<String> delayed() {
        return message -> {
            System.out.println("接收的延时消息为：" + message);
        };
    }
}