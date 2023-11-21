package blossom.project.towelove.common.annotaion;/**
 * @Author:Serendipity
 * @Date:
 * @Description:
 */

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: 张锦标
 * @date: 2023/10/6 21:22
 * Transaction注解
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Transactional
public @interface Transaction {
}
