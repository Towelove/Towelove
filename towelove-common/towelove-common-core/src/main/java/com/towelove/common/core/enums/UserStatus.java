package com.towelove.common.core.enums;

/**
 * 用户状态
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 */
public enum UserStatus
{
    OK("0", "正常"), DISABLE("1", "停用"), DELETED("2", "删除");

    private final String code;
    private final String info;

    UserStatus(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
