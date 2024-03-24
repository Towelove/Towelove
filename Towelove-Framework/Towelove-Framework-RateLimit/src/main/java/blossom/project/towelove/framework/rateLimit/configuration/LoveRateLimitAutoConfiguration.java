package blossom.project.towelove.framework.rateLimit.configuration;

import blossom.project.towelove.framework.rateLimit.aspect.RateLimitAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.framework.rateLimit.configuration
 * @className: LoveRateLimitAutoConfiguration
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/24 15:46
 * @version: 1.0
 */
@Configuration
@AutoConfiguration
public class LoveRateLimitAutoConfiguration {
    @Bean
    public RateLimitAspect rateLimitAspect(){
        return new RateLimitAspect();
    }
}
