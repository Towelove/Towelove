package com.towelove.system.domain.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.towelove.system.api.domain.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/4/9 21:48
 * UserInfoBaseVO类
 */
@Data
public class UserInfoBaseVO {
    /**
     * 用户ID
     */
    @Schema(description = "用户id", required = false, example = "1024")
    @NotNull(message = "用户id必填")
    private Long userId;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称", required = false, example = "用户昵称张锦标")
    private String nickName;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱", required = false, example = "用户邮箱")
    @Email
    private String email;

    /**
     * 邮箱加密编码
     * pop3-smtpCode
     */
    @Schema(description = "邮箱密码", required = false, example = "用户邮箱密码")
    private String smtpCode;

    /**
     * 手机号码
     */
    @Schema(description = "用户手机", required = false, example = "用户手机号")
    private String phonenumber;

    /**
     * 用户性别
     */
    @Schema(description = "用户性别", required = false, example = "用户性别")
    private String sex;


}
