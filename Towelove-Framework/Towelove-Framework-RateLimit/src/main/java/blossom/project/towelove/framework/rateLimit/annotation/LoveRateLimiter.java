package blossom.project.towelove.framework.rateLimit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.framework.rateLimit.annotation
 * @className: LoveRateLimiter
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/24 15:30
 * @version: 1.0
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface LoveRateLimiter {

    int qps();

    boolean enable() default true;
}
