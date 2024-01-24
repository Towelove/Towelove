package blossom.project.towelove.loves;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/30 13:47
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * ToweloveLovesApplication类
 */
@EnableDiscoveryClient
@EnableFeignClients("blossom.project.towelove.client")
@SpringBootApplication(scanBasePackages = "blossom.project.towelove")
public class ToweloveLovesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveLovesApplication.class,args);
        System.out.println("（づ￣3￣）づ╭❤～ Towelove Loves模块启动成功  (o>ε(o>ｕ(≧∩≦)");
        System.out.println(
                " _                         \n" +
                        "| |                        \n" +
                        "| |     _____   _____  ___ \n" +
                        "| |    / _ \\ \\ / / _ \\/ __|\n" +
                        "| |___| (_) \\ V /  __/\\__ \\\n" +
                        "|______\\___/ \\_/ \\___||___/\n");
    }
}
