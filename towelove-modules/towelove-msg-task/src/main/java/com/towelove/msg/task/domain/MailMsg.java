package com.towelove.msg.task.domain;

import com.towelove.system.api.domain.SysMailAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/3/16 9:12
 * MailMsg类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MailMsg extends MsgTask{
    //---------邮箱账号内容-------------
    @Schema(description = "发件人邮箱", required = true, example = "460219753@qq.com")
    @NotNull(message = "发件人邮箱不能为空")
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
