package com.towelove.system.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author: 张锦标
 * @date: 2023/3/16 9:29
 * MailAccountBaseVO类
 */
@Data
public class MailAccountBaseVO {
    @Schema(description = "用户id", required = true, example = "1024")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "邮箱", required = true, example = "yudaoyuanma@123.com")
    @NotNull(message = "邮箱不能为空")
    @Email(message = "必须是 Email 格式")
    private String mail;

    @Schema(description = "用户名", required = true, example = "yudao")
    @NotNull(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码", required = true, example = "123456")
    @NotNull(message = "密码必填")
    private String password;

    @Schema(description = "SMTP 服务器域名", required = true, example = "www.iocoder.cn")
    @NotNull(message = "SMTP 服务器域名不能为空")
    private String host;

    @Schema(description = "SMTP 服务器端口", required = true, example = "80")
    @NotNull(message = "SMTP 服务器端口不能为空")
    private Integer port;

    @Schema(description = "是否开启 ssl", required = true, example = "true")
    @NotNull(message = "是否开启 ssl 必填")
    private Boolean sslEnable;
}
