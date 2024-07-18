package blossom.project.towelove.treehole.config;

import blossom.project.towelove.treehole.model.enums.ArkEnum;
import com.volcengine.ark.runtime.service.ArkService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author sujia
 * @date 2024/7/18
 */

@Configuration
@Data
@RefreshScope
@ConfigurationProperties(prefix = "ark")
public class ArkConfig {
    private Logger logger = LoggerFactory.getLogger(ArkConfig.class);

    @Value("${ark.apiKey}")
    private String apiKey;

    @Value("${ark.endpointId}")
    private String endpointId;

    @Bean
    public ArkService getArkService() {
        logger.info("Initializing ArkService with API Key: {}", apiKey);
        ArkService arkService = new ArkService(apiKey);
        ArkEnum.INSTANCE.init(apiKey, endpointId);
        return arkService;
    }
}
