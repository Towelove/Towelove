package com.towelove.rocketmq.consumer;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/1 8:45
 * ToweloveRocketApplication类
 */
@SpringBootApplication
public class ToweloveRocketConsumerApplication  implements CommandLineRunner{

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    public static void main(String[] args) {
        SpringApplication.run(ToweloveRocketConsumerApplication.class,args);
    }
    @Override
    public void run(String... args) throws Exception {
        ////This is an example of pull consumer with access-key and secret-key.
        List<String> messages = rocketMQTemplate.receive(String.class);
        System.out.printf("receive from rocketMQTemplate, messages=%s %n", messages);
    }

}
