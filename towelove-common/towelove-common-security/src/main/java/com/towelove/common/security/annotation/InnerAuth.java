package com.towelove.common.security.annotation;

import java.lang.annotation.*;

/**
 * 内部认证注解
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerAuth
{
    /**
     * 是否校验用户信息
     */
    boolean isUser() default false;
}