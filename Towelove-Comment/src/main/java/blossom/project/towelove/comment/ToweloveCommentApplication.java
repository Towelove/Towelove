package blossom.project.towelove.treehole;

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
public class ToweloveCommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveCommentApplication.class,args);
        System.out.println("（づ￣3￣）づ╭❤～ Towelove评论区模块启动成功  (o>ε(o>ｕ(≧∩≦)");

    }
}
