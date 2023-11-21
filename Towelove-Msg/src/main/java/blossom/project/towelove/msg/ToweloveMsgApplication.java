package blossom.project.towelove.msg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/21 18:28
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MsgApplicatiojn类
 */
@MapperScan("blossom.project.towelove.msg.mapper")
@SpringBootApplication
public class ToweloveMsgApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveMsgApplication.class,args);
    }
}
