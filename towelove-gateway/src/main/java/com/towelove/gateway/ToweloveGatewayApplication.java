package com.towelove.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author: 张锦标
 * @date: 2023/2/23 18:46
 * Description:
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ToweloveGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveGatewayApplication.class, args);
    }
}
