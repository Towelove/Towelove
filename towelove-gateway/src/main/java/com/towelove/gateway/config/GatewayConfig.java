package com.towelove.gateway.config;

import com.towelove.gateway.handler.SentinelFallbackHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 网关限流配置
 * 
 * @author: 张锦标
 */
@Configuration
public class GatewayConfig
{
    //设定熔断优先级为最高
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE+1)
    public SentinelFallbackHandler sentinelGatewayExceptionHandler()
    {
        return new SentinelFallbackHandler();
    }
}