package com.towelove.system.api.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.towelove.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/2/24 9:39
 * Description:
 */
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotNull
    private Long userId;

    /**
     * 角色ID
     */
    @NotNull
    private Long roleId;
    /**
     * 用户账号
     */
    @NotBlank
    private String userName;

    /**
     * 用户昵称
     */
    @NotBlank
    private String nickName;

    /**
     * 用户邮箱
     */
    @NotBlank
    @NotNull
    private String email;

    /**
     * 邮箱加密编码
     * pop3-smtpCode
     */
    @NotBlank
    private String smtpCode;

    /**
     * 手机号码
     */
    @NotBlank
    private String phonenumber;

    /**
     * 用户性别
     */
    @NotBlank
    private String sex;

    /**
     * 用户头像
     */
    @NotBlank
    private String avatar;

    /**
     * 密码
     */
    @NotBlank
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @NotBlank
    private String status;


    /**
     * 最后登录IP
     */
    @NotBlank
    private String loginIp;

    /**
     * 用户类型 默认2为普通用户
     */
    @NotBlank
    private String userType;
    /**
     * 最后登录时间
     */
    @TableField(exist = false)
    @NotNull
    private Date loginDate;

    /**
     * 角色对象
     */
    @TableField(exist = false)
    private List<SysRole> roles;
    /**
     * 角色组
     */
    @TableField(exist = false)
    private Long[] roleIds;

    @TableField(exist = false)
    private String InvitationCode;



    public SysUser() {

    }

    public String getInvitationCode() {
        return InvitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        InvitationCode = invitationCode;
    }

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public String getSmtpCode() {
        return smtpCode;
    }

    public void setSmtpCode(String smtpCode) {
        this.smtpCode = smtpCode;
    }

    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }


    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId()).append(
                "userName", getUserName())
                .append("nickName", getNickName()).append("email", getEmail())
                .append("smtpCode", getSmtpCode())
                .append("phonenumber", getPhonenumber())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("password", getPassword())
                .append("status", getStatus())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime()).append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
    }
}
