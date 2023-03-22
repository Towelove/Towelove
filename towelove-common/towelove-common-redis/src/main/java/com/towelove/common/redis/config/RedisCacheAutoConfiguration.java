package com.towelove.common.redis.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author: 张锦标
 * @date: 2023/3/22 17:29
 * RedisCacheAutoConfiguration类
 * Spring-cache将使用redis作为项目缓存
 */
@AutoConfiguration
@EnableConfigurationProperties({CacheProperties.class})//允许将配置属性配置类放入spring容器中
@EnableCaching
public class RedisCacheAutoConfiguration {
    /**
     * RedisCacheConfiguration Bean
     *
     * 参考 org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     * 的 createConfiguration 方法
     */
    @Bean
    @Primary
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
        // 设置使用 JSON 序列化方式
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        config = config
                .serializeKeysWith( //设定键的序列化方式
                RedisSerializationContext.SerializationPair.
                        fromSerializer(new StringRedisSerializer())).
                serializeValuesWith( //设定值的序列化方式
                RedisSerializationContext.SerializationPair
                        .fromSerializer(RedisSerializer.json()));

        // 设置 CacheProperties.Redis 的属性
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }
}
