package com.towelove.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: 张锦标
 * @date: 2023/2/23 20:10
 * Description:
 */
@SpringBootApplication
public class ToweloveSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveSystemApplication.class,args);
        System.out.println("Towelove系统模块启动成功");
    }
}
