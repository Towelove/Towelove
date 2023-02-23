package com.towelove.common.core.exception.auth;

/**
 * 未能通过的登录认证异常
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:46
 * Description:
 */
public class NotLoginException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public NotLoginException(String message)
    {
        super(message);
    }
}
