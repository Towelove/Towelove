package com.towelove.common.core.exception;

/**
 * 工具类异常
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:46
 * Description:
 */
public class UtilException extends RuntimeException
{
    private static final long serialVersionUID = 8247610319171014183L;

    public UtilException(Throwable e)
    {
        super(e.getMessage(), e);
    }

    public UtilException(String message)
    {
        super(message);
    }

    public UtilException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
