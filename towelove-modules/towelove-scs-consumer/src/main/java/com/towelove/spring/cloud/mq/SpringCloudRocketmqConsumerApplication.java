package com.towelove.spring.cloud.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @author: 张锦标
 * @date: 2023/3/2 20:23
 * SpringCloudStreamApplication类
 */
@EnableBinding(Sink.class)
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringCloudRocketmqConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudRocketmqConsumerApplication.class,args);
    }
}
