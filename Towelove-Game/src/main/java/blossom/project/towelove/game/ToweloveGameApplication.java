package blossom.project.towelove.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/27 13:31
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * ToweloveGameApplication类
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ToweloveGameApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToweloveGameApplication.class, args);
    }
}
