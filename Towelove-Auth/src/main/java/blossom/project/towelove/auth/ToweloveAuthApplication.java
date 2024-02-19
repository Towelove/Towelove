package blossom.project.towelove.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients("blossom.project.towelove.client")
@ComponentScan(basePackages = {"blossom.project.towelove"})
public class ToweloveAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveAuthApplication.class,args);
        System.out.println("（づ￣3￣）づ╭❤～ Towelove鉴权模块启动成功  (o>ε(o>ｕ(≧∩≦)");
        System.out.println("" +
                "               _   _     \n" +
                "    /\\        | | | |    \n" +
                "   /  \\  _   _| |_| |__  \n" +
                "  / /\\ \\| | | | __| '_ \\ \n" +
                " / ____ \\ |_| | |_| | | |\n" +
                "/_/    \\_\\__,_|\\__|_| |_|\n");
    }
}
