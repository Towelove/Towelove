package blossom.project.towelove.shortUrl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/21 18:28
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MsgApplicatiojn类
 */
@SpringBootApplication
@MapperScan("blossom.project.towelove.shortUrl.mapper")
@EnableFeignClients("blossom.project.towelove.client")
public class ToweloveMsgApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveMsgApplication.class,args);

        System.out.println("（づ￣3￣）づ╭❤～ Towelove 消息模块启动成功  (o>ε(o>ｕ(≧∩≦)");
        System.out.println(
               " __  __           \n" +
                       "|  \\/  |          \n" +
                       "| \\  / |___  __ _ \n" +
                       "| |\\/| / __|/ _` |\n" +
                       "| |  | \\__ \\ (_| |\n" +
                       "|_|  |_|___/\\__, |\n" +
                       "             __/ |\n" +
                       "            |___/ \n");

    }
}