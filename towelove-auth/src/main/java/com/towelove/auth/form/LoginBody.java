package com.towelove.auth.form;

/**
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 * 用户登录对象
 */
public class LoginBody {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户登录时候用的密码
     */
    private String password;
    /**
     * 用户新密码
     */
    private String newPassword;
    /**
     * 用户旧密码
     */
    private String oldPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String uuid;
    private String code;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
