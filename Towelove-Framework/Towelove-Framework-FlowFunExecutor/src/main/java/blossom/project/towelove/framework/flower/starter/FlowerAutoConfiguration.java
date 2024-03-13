package blossom.project.towelove.framework.flower.starter;

import blossom.project.towelove.framework.flower.executor.FlowExecutor;
import blossom.project.towelove.framework.flower.extension.ExtensionExecutor;
import blossom.project.towelove.framework.flower.register.ActivityRegister;
import blossom.project.towelove.framework.flower.register.ExtensionRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author: 张锦标
 * @date: 2024/3/12 3:08 PM
 * FlowerAutoConfiguration类
 * 函数编排执行工具flower自动配置类
 */
@AutoConfiguration
public class FlowerAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(FlowerAutoConfiguration.class);

    public FlowerAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean({FlowExecutor.class})
    public FlowExecutor initFlowExecutor() {
        return new FlowExecutor();
    }

    @Bean
    @ConditionalOnMissingBean({ActivityRegister.class})
    public ActivityRegister initActivityRegister() {
        return new ActivityRegister();
    }

    @Bean
    @ConditionalOnMissingBean({ExtensionRegister.class})
    public ExtensionRegister initExtensionRegister() {
        return new ExtensionRegister();
    }

    @Bean
    @ConditionalOnMissingBean({ExtensionExecutor.class})
    public ExtensionExecutor initExtensionExecutor() {
        return new ExtensionExecutor();
    }


}
