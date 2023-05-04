package com.towelove.auth.form;

import lombok.Data;

/**
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 * 用户注册对象
 */
@Data
public class RegisterBody
{
    /**
     * 用户名 唯一
     */
    private String username;
    /**
     * 用户昵称 展示作用
     */
    private String nickname;
    /**
     * 用户输入的密码
     */
    private String password;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户手机号
     */
    private String phonenumber;
    /**
     * 用户性别
     */
    private String sex;

    private String invitedCode;
}
