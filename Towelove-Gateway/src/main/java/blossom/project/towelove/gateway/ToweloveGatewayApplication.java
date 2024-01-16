package blossom.project.towelove.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: 张锦标
 * @date: 2023/2/23 18:46
 * Description:
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class },
scanBasePackages = "blossom.project.towelove")
@EnableFeignClients("blossom.project.towelove.client")
public class ToweloveGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveGatewayApplication.class, args);
        System.out.println("（づ￣3￣）づ╭❤～ Towelove网关模块启动成功  (o>ε(o>ｕ(≧∩≦)");
        System.out.println(
                "   _____           _                                     \n" +
                "  / ____|         | |                                    \n" +
                " | |  __    __ _  | |_    ___  __      __   __ _   _   _ \n" +
                " | | |_ |  / _` | | __|  / _ \\ \\ \\ /\\ / /  / _` | | | | |\n" +
                " | |__| | | (_| | | |_  |  __/  \\ V  V /  | (_| | | |_| |\n" +
                "  \\_____|  \\__,_|  \\__|  \\___|   \\_/\\_/    \\__,_|  \\__, |\n" +
                "                                                    __/ |\n" +
                "                                                   |___/ \n");
    }
}
