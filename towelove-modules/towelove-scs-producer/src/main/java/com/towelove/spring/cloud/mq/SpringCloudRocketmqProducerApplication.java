package com.towelove.spring.cloud.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

/**
 * @author: 张锦标
 * @date: 2023/3/2 20:23
 * SpringCloudStreamApplication类
 */
@EnableBinding(Source.class)
@SpringBootApplication
public class SpringCloudRocketmqProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudRocketmqProducerApplication.class,args);
    }
}
