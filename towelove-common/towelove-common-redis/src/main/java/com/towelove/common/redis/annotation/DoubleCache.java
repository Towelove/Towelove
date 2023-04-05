package com.towelove.common.redis.annotation;/**
 * @Author:Serendipity
 * @Date:
 * @Description:
 */


import com.towelove.common.redis.enums.CacheType;

import java.lang.annotation.*;

/**
 * @author: 张锦标
 * @date: 2023/4/5 17:45
 * DoubleCache注解
 * 自定义二级缓存注解
 * 我们使用cacheName + key作为缓存的真正key
 * （仅存在一个Cache中，不做CacheName隔离），
 * l2TimeOut为可以设置的二级缓存Redis的过期时间，
 * type是一个枚举类型的变量，
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoubleCache {
    String cacheName();

    String key(); //支持springEl表达式

    long l2TimeOut() default 120;
    //表示操作缓存的类型，枚举类型定义如下：
    CacheType type() default CacheType.GET;
}
