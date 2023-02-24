package com.towelove.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: 张锦标
 * @date: 2023/2/24 9:49
 * Description:
 */
@EnableFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ToweloveAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveAuthApplication.class,args);
        System.out.println("-------Towelove权限校验模块启动成功--------");
    }

}
