package com.towelove.common.core.exception.user;

/**
 * 验证码失效异常类
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:46
 * Description:
 */
public class CaptchaExpireException extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException()
    {
        super("user.jcaptcha.expire", null);
    }
}
