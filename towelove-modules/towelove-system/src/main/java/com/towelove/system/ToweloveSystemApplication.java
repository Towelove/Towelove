package com.towelove.system;

import cn.hutool.extra.mail.MailUtil;
import com.towelove.common.security.annotation.EnableCustomConfig;
import com.towelove.common.swagger.annotation.EnableCustomSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author: 张锦标
 * @date: 2023/2/23 20:10
 * Description:
 */

//@EnableBinding
@EnableCustomConfig
@EnableFeignClients
@EnableCustomSwagger2
@SpringBootApplication
public class ToweloveSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveSystemApplication.class,args);
        System.out.println("（づ￣3￣）づ╭❤～ Towelove系统模块启动成功  (o>ε(o>ｕ(≧∩≦)");
        System.out.println(
                "   _____                 _                      \n" +
                "  / ____|               | |                     \n" +
                " | (___    _   _   ___  | |_    ___   _ __ ___  \n" +
                "  \\___ \\  | | | | / __| | __|  / _ \\ | '_ ` _ \\ \n" +
                "  ____) | | |_| | \\__ \\ | |_  |  __/ | | | | | |\n" +
                " |_____/   \\__, | |___/  \\__|  \\___| |_| |_| |_|\n" +
                "            __/ |                               \n" +
                "           |___/                                \n");

    }
}
