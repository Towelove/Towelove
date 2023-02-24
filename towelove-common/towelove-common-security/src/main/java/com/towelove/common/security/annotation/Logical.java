package com.towelove.common.security.annotation;

/**
 * 权限注解的验证模式
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 *
 */
public enum Logical
{
    /**
     * 必须具有所有的元素
     */
    AND,

    /**
     * 只需具有其中一个元素
     */
    OR
}
