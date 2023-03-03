package com.towelove.rocketmq.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author: 张锦标
 * @date: 2023/3/1 8:45
 * ToweloveRocketApplication类
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ToweloveRocketProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveRocketProducerApplication.class,args);
    }
}
