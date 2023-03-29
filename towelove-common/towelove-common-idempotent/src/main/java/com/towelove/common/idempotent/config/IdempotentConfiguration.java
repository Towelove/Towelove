package com.towelove.common.idempotent.config;


import com.towelove.common.idempotent.aop.IdempotentAspect;
import com.towelove.common.idempotent.keyresolver.IdempotentKeyResolver;
import com.towelove.common.idempotent.keyresolver.impl.DefaultIdempotentKeyResolver;
import com.towelove.common.idempotent.keyresolver.impl.ExpressionIdempotentKeyResolver;
import com.towelove.common.idempotent.redis.IdempotentRedisDAO;
import com.towelove.common.redis.config.RedisConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration
@AutoConfigureAfter(RedisConfig.class)
public class IdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers,
                                             IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
