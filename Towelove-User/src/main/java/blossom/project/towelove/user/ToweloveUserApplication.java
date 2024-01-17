package blossom.project.towelove.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "blossom.project.towelove")
public class ToweloveUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveUserApplication.class,args);
        System.out.println("（づ￣3￣）づ╭❤～ Towelove用户模块启动成功  (o>ε(o>ｕ(≧∩≦)");
        System.out.println("" +
                " _    _               \n" +
                "| |  | |              \n" +
                "| |  | |___  ___ _ __ \n" +
                "| |  | / __|/ _ \\ '__|\n" +
                "| |__| \\__ \\  __/ |   \n" +
                " \\____/|___/\\___|_|   \n" +
                "\n");
    }
}
