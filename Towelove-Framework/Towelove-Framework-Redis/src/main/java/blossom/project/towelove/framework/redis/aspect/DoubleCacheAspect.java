package blossom.project.towelove.framework.redis.aspect;

import blossom.project.towelove.common.constant.CaffeineCacheConstant;
import blossom.project.towelove.framework.redis.annotation.DoubleCache;
import blossom.project.towelove.framework.redis.enums.CacheType;
import blossom.project.towelove.framework.redis.util.ElParser;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * @author: 张锦标
 * @date: 2023/4/5 17:49
 * DoubleCacheAspect自定义切面类
 * 当前类用于对所有的使用了DoubleCache的注解进行aop操作
 * 暂不支持使用#p0这种方式
 * <p>
 * 切面中主要做了下面几件工作：
 * <p>
 * 通过方法的参数，解析注解中key的springEl表达式，组装真正缓存的key
 * 根据操作缓存的类型，分别处理存取、只存、删除缓存操作
 * 删除和强制更新缓存的操作，都需要执行原方法，并进行相应的缓存删除或更新操作
 * 存取操作前，先检查缓存中是否有数据，如果有则直接返回，没有则执行原方法，并将结果存入缓存
 * <p>
 * 使用方式
 * @DoubleCache(cacheName = "order", key = "#id",type = CacheType.FULL)
 * @DoubleCache(cacheName = "order",key = "#order.id",type = CacheType.PUT)
 * @DoubleCache(cacheName = "order",key = "#id",type = CacheType.DELETE)
 */
@Slf4j
@Aspect
@AllArgsConstructor
@AutoConfiguration
public class DoubleCacheAspect {


    private final Cache cache;

    @Autowired
    private final RedisTemplate redisTemplate;

    @Pointcut("@annotation(blossom.project.towelove.framework.redis.annotation.DoubleCache)")
    public void cacheAspect() {
    }

    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        //拼接解析springEl表达式的map
        String[] paramNames = signature.getParameterNames();
        Object[] args = point.getArgs();
        TreeMap<String, Object> treeMap = new TreeMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            treeMap.put(paramNames[i], args[i]);
        }

        DoubleCache annotation = method.getAnnotation(DoubleCache.class);
        String elResult = ElParser.parse(annotation.key(), treeMap);
        String realKey = annotation.cacheName() + CaffeineCacheConstant.DOUBLECACHE + elResult;

        //强制更新
        if (annotation.type() == CacheType.PUT) {
            Object object = point.proceed();
            redisTemplate.opsForValue().set(realKey, object, annotation.l2TimeOut(), TimeUnit.SECONDS);
            cache.put(realKey, object);
            return object;
        }
        //删除
        else if (annotation.type() == CacheType.DELETE) {
            redisTemplate.delete(realKey);
            cache.invalidate(realKey);
            return point.proceed();
        }

        //读写，查询Caffeine
        Object caffeineCache = cache.getIfPresent(realKey);
        if (Objects.nonNull(caffeineCache)) {
            log.info("get data from caffeine");
            return caffeineCache;
        }

        //查询Redis
        Object redisCache = redisTemplate.opsForValue().get(realKey);
        if (Objects.nonNull(redisCache)) {
            log.info("get data from redis");
            cache.put(realKey, redisCache);
            return redisCache;
        }

        log.info("get data from database");
        Object object = point.proceed();
        if (Objects.nonNull(object)) {
            //写入Redis
            redisTemplate.opsForValue().set(realKey,
                    object, annotation.l2TimeOut(), TimeUnit.SECONDS);
            //写入Caffeine
            cache.put(realKey, object);
        }
        return object;
    }
}