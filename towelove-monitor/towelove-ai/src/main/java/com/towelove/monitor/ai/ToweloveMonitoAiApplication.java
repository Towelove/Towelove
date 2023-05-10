package com.towelove.monitor.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: 张锦标
 * @date: 2023/3/8 12:47
 * ToweloveMonitorMQApplication类
 * 当前项目用于监控MQ消息队列
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ToweloveMonitoAiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveMonitoAiApplication.class,args);
        System.out.println("（づ￣3￣）づ╭❤～ Towelove-Ai模块启动成功  (o>ε(o>ｕ(≧∩≦)");
        System.out.println("\n" +
                "  __  __                   _   _                  \n" +
                " |  \\/  |                 (_) | |                 \n" +
                " | \\  / |   ___    _ __    _  | |_    ___    _ __ \n" +
                " | |\\/| |  / _ \\  | '_ \\  | | | __|  / _ \\  | '__|\n" +
                " | |  | | | (_) | | | | | | | | |_  | (_) | | |   \n" +
                " |_|  |_|  \\___/  |_| |_| |_|  \\__|  \\___/  |_|   \n" +
                "                                                  \n" +
                "                                                  \n");
    }
}
