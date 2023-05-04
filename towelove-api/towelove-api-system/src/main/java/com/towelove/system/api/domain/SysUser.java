package com.towelove.system.api.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.towelove.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotNull
    @TableId("user_id")
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 用户账号
     */
    @NotBlank
    @JsonProperty(value="userName")
    private String userName;

    /**
     * 用户昵称
     */
    @NotBlank
    @JsonProperty(value="nickName")
    private String nickName;

    /**
     * 用户邮箱
     */
    @NotBlank
    @NotNull
    @JsonProperty(value="email")
    private String email;

    /**
     * 邮箱加密编码
     * pop3-smtpCode
     */
    private String smtpCode;

    @TableField(exist = false)
    @JsonProperty(value="invitedCode")
    private String invitedCode;
    /**
     * 手机号码
     */
    @JsonProperty(value="phonenumber")
    private String phonenumber;

    /**
     * 用户性别
     */
    @JsonProperty(value="sex")
    private String sex;

    /**
     * 用户头像
     */

    private String avatar;

    /**
     * 密码
     */
    @NotBlank
    @JsonProperty(value="password")
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;


    /**
     * 最后登录IP
     */

    private String loginIp;

    /**
     * 用户类型 默认2为普通用户
     */
    private String userType;
    /**
     * 最后登录时间
     */
    @TableField(exist = false)
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


}
