package blossom.project.towelove.framework.dtf.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 17:49 2024/1/29
 */
@Configuration
public class CustomizedBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println(" " +
                " ____                                        _          _____   _                                 _   ____                    _ \n" +
                " |  _ \\   _   _   _ __     __ _   _ __ ___   (_)   ___  |_   _| | |__    _ __    ___    __ _    __| | |  _ \\    ___     ___   | |\n" +
                " | | | | | | | | | '_ \\   / _` | | '_ ` _ \\  | |  / __|   | |   | '_ \\  | '__|  / _ \\  / _` |  / _` | | |_) |  / _ \\   / _ \\  | |\n" +
                " | |_| | | |_| | | | | | | (_| | | | | | | | | | | (__    | |   | | | | | |    |  __/ | (_| | | (_| | |  __/  | (_) | | (_) | | |\n" +
                " |____/   \\__, | |_| |_|  \\__,_| |_| |_| |_| |_|  \\___|   |_|   |_| |_| |_|     \\___|  \\__,_|  \\__,_| |_|      \\___/   \\___/  |_|\n" +
                "          |___/                                                                                                                      2.0 ");
    }


}