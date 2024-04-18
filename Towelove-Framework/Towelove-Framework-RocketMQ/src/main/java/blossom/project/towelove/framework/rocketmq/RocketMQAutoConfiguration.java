package blossom.project.towelove.framework.rocketmq;

import blossom.project.towelove.framework.rocketmq.service.RocketMQSender;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2024/4/18 5:06 PM
 * RocketMQAutoConfiguration类
 */
@Component
@ConditionalOnClass(value = RocketMQListener.class)
public class RocketMQAutoConfiguration {
    @Bean
    public RocketMQSender rocketMQService(){
        return new RocketMQSender();
    }

}
