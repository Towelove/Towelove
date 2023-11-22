package blossom.project.towelove.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients("blossom.project.towelove.client")
public class ToweloveAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveAuthApplication.class,args);
    }
}
