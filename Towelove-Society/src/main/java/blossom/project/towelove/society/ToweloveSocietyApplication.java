package blossom.project.towelove.society;

import blossom.project.towelove.society.config.MyImportSelector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/5 10:52
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * ToweloveSocietyApplication类
 */
@SpringBootApplication
@Import(MyImportSelector.class)
public class ToweloveSocietyApplication {
    public static void main(String[] args) {

        ApplicationContext context1 =
                new AnnotationConfigApplicationContext(ToweloveSocietyApplication.class);

        ConfigurableApplicationContext context =
                SpringApplication.run(ToweloveSocietyApplication.class, args);
    }
}
