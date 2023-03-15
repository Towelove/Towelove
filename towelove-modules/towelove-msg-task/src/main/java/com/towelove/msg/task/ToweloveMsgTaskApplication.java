package com.towelove.msg.task;

import com.towelove.common.security.annotation.EnableCustomConfig;
import com.towelove.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:32
 * ToweloveMsgTaskApplication类
 */
@EnableCustomConfig
@EnableCustomSwagger2
@SpringBootApplication
@EnableFeignClients(basePackages = "com.towelove")
public class ToweloveMsgTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveMsgTaskApplication.class,args);
        System.out.println("（づ￣3￣）づ╭❤～ Towelove消息任务模块启动成功  (o>ε(o>ｕ(≧∩≦)");
        System.out.println(
                "  __  __                   _______                 _    \n" +
                " |  \\/  |                 |__   __|               | |   \n" +
                " | \\  / |  ___    __ _       | |      __ _   ___  | | __\n" +
                " | |\\/| | / __|  / _` |      | |     / _` | / __| | |/ /\n" +
                " | |  | | \\__ \\ | (_| |      | |    | (_| | \\__ \\ |   < \n" +
                " |_|  |_| |___/  \\__, |      |_|     \\__,_| |___/ |_|\\_\\\n" +
                "                  __/ |                                 \n" +
                "                 |___/                                  \n");
    }
}
