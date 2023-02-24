package com.towelove.common.security.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

/**
 * 自定义feign注解
 * 添加basePackages路径
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
public @interface EnableRyFeignClients
{
    String[] value() default {};

    String[] basePackages() default { "com.towelove" };

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}
