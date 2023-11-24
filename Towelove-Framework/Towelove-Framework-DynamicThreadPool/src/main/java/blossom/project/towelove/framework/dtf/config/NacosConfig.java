package blossom.project.towelove.framework.dtf.config;


import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Properties;
 
/**
 * nacos配置
 *
 * @author 张锦标
 */
@AutoConfiguration
public class NacosConfig {
    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;
    @Value("${spring.cloud.nacos.config.namespace:public}")
    private String namespace;
 
    @Bean
    @Primary
    public ConfigService configService() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        properties.put("namespace", namespace);
        try {
            return NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            throw new NacosException(555,"nacos配置中心创建异常",e);
        }
    }
}