package blossom.project.towelove.framework.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: 张锦标
 * @date: 2023/8/22 14:27
 * Dlock注解
 * Redis分布式锁注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Dlock {
    String value();
    boolean sort() default false;
}
