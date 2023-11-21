package blossom.project.towelove.framework.redis.annotation;/**
 * @Author:Serendipity
 * @Date:
 * @Description:
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: 张锦标
 * @date: 2023/8/22 14:28
 * DlockKey注解
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface DlockKey {
}
