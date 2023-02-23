package com.towelove.common.core.exception.user;

import com.towelove.common.core.exception.base.BaseException;

;

/**
 * 用户信息异常类
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:46
 * Description:
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
