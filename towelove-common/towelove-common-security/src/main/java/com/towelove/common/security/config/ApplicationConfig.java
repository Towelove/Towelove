package com.towelove.common.security.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

/**
 * 系统配置
 *
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 */
public class ApplicationConfig
{
    /**
     * 时区配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization()
    {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
    }
}
