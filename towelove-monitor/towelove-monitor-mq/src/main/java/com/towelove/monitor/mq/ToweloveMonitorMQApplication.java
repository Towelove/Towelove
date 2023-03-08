package com.towelove.monitor.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author: 张锦标
 * @date: 2023/3/8 12:47
 * ToweloveMonitorMQApplication类
 * 当前项目用于监控MQ消息队列
 */

//添加 Spring Cloud Bus 定义的 @RemoteApplicationEventScan 注解，
// 声明要从 Spring Cloud Bus 监听 RemoteApplicationEvent 事件。
@RemoteApplicationEventScan(basePackages = "com.towelove.monitor.mq.event.*")
@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ToweloveMonitorMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveMonitorMQApplication.class,args);
        System.out.println("（づ￣3￣）づ╭❤～ Towelove消息队列监控模块启动成功  (o>ε(o>ｕ(≧∩≦)");
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
