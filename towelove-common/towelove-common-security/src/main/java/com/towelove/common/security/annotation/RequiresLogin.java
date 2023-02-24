package com.towelove.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录认证：只有登录之后才能进入该方法
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RequiresLogin
{
}
