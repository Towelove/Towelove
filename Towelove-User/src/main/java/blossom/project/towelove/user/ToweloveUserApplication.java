package blossom.project.towelove.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "blossom.project.towelove")
public class ToweloveUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveUserApplication.class,args);
    }
}
