package com.towelove.system.api.model;

import lombok.Data;

/**
 * @author: 张锦标
 * @date: 2023/4/22 22:09
 * SysUserRespVO类
 */
@Data
public class SysUserRespVO {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 邮箱加密编码
     * pop3-smtpCode
     */
    //private String smtpCode;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户头像地址
     */
    private String avatar;


}
