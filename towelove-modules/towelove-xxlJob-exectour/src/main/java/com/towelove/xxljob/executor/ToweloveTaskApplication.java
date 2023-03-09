package com.towelove.xxljob.executor;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 季台星
 * @version 1.0
 */
@SpringBootApplication(exclude = MybatisPlusAutoConfiguration.class)
@EnableFeignClients(basePackages = "com.towelove")
public class ToweloveTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveTaskApplication.class, args);
    }
}