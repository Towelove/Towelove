package blossom.project.towelove.framework.redis.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author: 张锦标
 * @date: 2023/2/24 9:49
 * Description:
 */
@Configuration
@EnableCaching
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisConfig extends CachingConfigurerSupport
{
    //@Bean
    //@SuppressWarnings(value = { "unchecked", "rawtypes" })
    //public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory)
    //{
    //    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    //    template.setConnectionFactory(connectionFactory);
    //
    //    FastJson2JsonRedisSerializer serializer = new FastJson2JsonRedisSerializer(Object.class);
    //
    //    // 使用StringRedisSerializer来序列化和反序列化redis的key值
    //    template.setKeySerializer(new StringRedisSerializer());
    //    template.setValueSerializer(serializer);
    //
    //    // Hash的key也采用StringRedisSerializer的序列化方式
    //    template.setHashKeySerializer(new StringRedisSerializer());
    //    template.setHashValueSerializer(serializer);
    //
    //    template.afterPropertiesSet();
    //    return template;
    //}
    /**
     * 创建 RedisTemplate Bean，使用 JSON 序列化方式
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 创建 RedisTemplate 对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置 RedisConnection 工厂。😈 它就是实现多种 Java Redis 客户端接入的秘密工厂。感兴趣的胖友，可以自己去撸下。
        template.setConnectionFactory(factory);
        // 使用 String 序列化方式，序列化 KEY 。
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        // 使用 JSON 序列化方式（库是 Jackson ），序列化 VALUE 。
        template.setValueSerializer(RedisSerializer.json());
        template.setHashValueSerializer(RedisSerializer.json());
        return template;
    }
}
