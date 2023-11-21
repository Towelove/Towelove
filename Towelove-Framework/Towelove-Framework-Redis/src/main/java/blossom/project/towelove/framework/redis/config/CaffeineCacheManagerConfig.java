package blossom.project.towelove.framework.redis.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;
/**
 * @author: 张锦标
 * @date: 2023/4/5 15:32
 * CacheMangerConfig类
 * spring项目中，提供了CacheManager接口和一些注解，
 * 许让我们通过注解的方式来操作缓存
 * 不需要配置V1版本中的那个类型为Cache的Bean了，
 * 而是需要配置spring中的CacheManager的相关参数，
 * 具体参数的配置和之前一样
 *
 * 项目中优先使用的是caffeine作为springcache缓存方案
 * 因为本地缓存还是会占用本机过多的内存的
 */
@AutoConfiguration
@EnableCaching
public class CaffeineCacheManagerConfig {
    @Bean
    @Primary
    public CacheManager cacheManager(){
        CaffeineCacheManager cacheManager=new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(128)
                .maximumSize(1024)
                .expireAfterWrite(60, TimeUnit.SECONDS));
        return cacheManager;
    }
}