package com.towelove.core;

import com.towelove.common.security.annotation.EnableCustomConfig;
import com.towelove.common.security.annotation.EnableToweloveFeignClients;
import com.towelove.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:32
 * ToweloveFileApplication类
 */
@EnableCustomConfig
@EnableToweloveFeignClients
@EnableCustomSwagger2
@SpringBootApplication
public class ToweloveCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveCoreApplication.class,args);
        System.out.println("（づ￣3￣）づ╭❤～ Towelove核心模块启动成功  (o>ε(o>ｕ(≧∩≦)");
        System.out.println("\n" +
                "   _____                       \n" +
                "  / ____|                      \n" +
                " | |        ___    _ __    ___ \n" +
                " | |       / _ \\  | '__|  / _ \\\n" +
                " | |____  | (_) | | |    |  __/\n" +
                "  \\_____|  \\___/  |_|     \\___|\n" +
                "                               \n" +
                "                               \n");
    }
}
