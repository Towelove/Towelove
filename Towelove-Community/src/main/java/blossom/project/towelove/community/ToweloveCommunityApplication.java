package blossom.project.towelove.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: ZhangBlossom
 * @date: 2024/6/7 13:08
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@SpringBootApplication(scanBasePackages = "blossom.project.towelove",
        exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients("blossom.project.towelove.client")
@ComponentScan(basePackages = {"blossom.project.towelove"})
public class ToweloveCommunityApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveCommunityApplication.class,args);
        System.out.println("（づ￣3￣）づ╭❤～ Towelove社区模块启动成功  (o>ε(o>ｕ(≧∩≦)");
        System.out.println("  _____                                      _ _         \n" +
                " / ____|                                    (_) |        \n" +
                "| |     ___  _ __ ___  _ __ ___  _   _ _ __  _| |_ _   _ \n" +
                "| |    / _ \\| '_ ` _ \\| '_ ` _ \\| | | | '_ \\| | __| | | |\n" +
                "| |___| (_) | | | | | | | | | | | |_| | | | | | |_| |_| |\n" +
                " \\_____\\___/|_| |_| |_|_| |_| |_|\\__,_|_| |_|_|\\__|\\__, |\n" +
                "                                                    __/ |\n" +
                "                                                   |___/ \n");
    }
}
