package com.towelove.auth;

import com.towelove.common.security.annotation.EnableCustomConfig;
import com.towelove.common.security.annotation.EnableToweloveFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: 张锦标
 * @date: 2023/2/24 9:49
 * Description:
 */
@EnableToweloveFeignClients
@EnableCustomConfig
@SpringBootApplication
public class ToweloveAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveAuthApplication.class, args);
        System.out.println("（づ￣3￣）づ╭❤～ Towelove权限校验模块启动成功  (o>ε(o>ｕ(≧∩≦)");
        System.out.println(
                "                     _     _     \n" +
                "     /\\             | |   | |    \n" +
                "    /  \\     _   _  | |_  | |__  \n" +
                "   / /\\ \\   | | | | | __| | '_ \\ \n" +
                "  / ____ \\  | |_| | | |_  | | | |\n" +
                " /_/    \\_\\  \\__,_|  \\__| |_| |_|\n" +
                "                                 \n" +
                "                                 \n");
    }
}
