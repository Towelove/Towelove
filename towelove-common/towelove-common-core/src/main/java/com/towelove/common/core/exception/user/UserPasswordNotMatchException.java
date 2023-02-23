package com.towelove.common.core.exception.user;

/**
 * 用户密码不正确或不符合规范异常类
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:46
 * Description:
 */
public class UserPasswordNotMatchException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException()
    {
        super("user.password.not.match", null);
    }
}
