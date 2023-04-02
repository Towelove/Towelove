package com.towelove.file;

import com.towelove.common.security.annotation.EnableCustomConfig;
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
@EnableFeignClients
@EnableCustomSwagger2
@SpringBootApplication
public class ToweloveFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveFileApplication.class,args);
        System.out.println("（づ￣3￣）づ╭❤～ Towelove文件管理模块启动成功  (o>ε(o>ｕ(≧∩≦)");
        System.out.println(
                "  ______   _   _        \n" +
                " |  ____| (_) | |       \n" +
                " | |__     _  | |   ___ \n" +
                " |  __|   | | | |  / _ \\\n" +
                " | |      | | | | |  __/\n" +
                " |_|      |_| |_|  \\___|\n" +
                "                        \n" +
                "                        \n");
    }
}
