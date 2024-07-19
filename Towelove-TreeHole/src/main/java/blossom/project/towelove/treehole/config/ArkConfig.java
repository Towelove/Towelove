package blossom.project.towelove.treehole.config;

import com.volcengine.ark.runtime.service.ArkService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ark配置类，用于配置Ark服务的相关参数以及初始化ArkService
 *
 * @author sujia
 * @date 2024/7/18
 */

@Configuration
@Data
public class ArkConfig {
    private Logger logger = LoggerFactory.getLogger(ArkConfig.class);

    @Value("${ark.apiKey}")
    private String apiKey;

    @Value("${ark.models.doubao-lite-128k}")
    private String doubaoLite128kEndpoint;

    @Value("${ark.models.doubao-lite-32k}")
    private String doubaoLite32kEndpoint;

    @Value("${ark.models.doubao-lite-4k}")
    private String doubaoLite4kEndpoint;

    @Value("${ark.models.doubao-pro-4k}")
    private String doubaoPro4kEndpoint;

    @Value("${ark.models.doubao-pro-128k}")
    private String doubaoPro128kEndpoint;

    @Value("${ark.models.doubao-pro-32k}")
    private String doubaoPro32kEndpoint;

    @Bean
    public ArkService getArkService() {
        logger.info("Initializing ArkService with API Key: {}", apiKey);
        return new ArkService(apiKey);
    }

}
