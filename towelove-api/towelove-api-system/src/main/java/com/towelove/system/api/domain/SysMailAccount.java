package com.towelove.system.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/3/14 10:14
 * SysMailAccount类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysMailAccount {
    //---------邮箱账号内容-------------
    @Schema(description = "id", required = true, example = "1024")
    @NotNull(message = "id不能为空")
    private Long id;

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

    //---------消息内容-----------

    @Schema(description = "当前用户账号id", required = true, example = "1024")
    @NotNull(message = "账号id必填")
    private Long accountId;

    @Schema(description = "模板id", required = true, example = "1024")
    private Long templateId;

    @Schema(description = "发送人名称", required = false, example = "zjb")
    private String nickname;

    @Schema(description = "标题", required = true, example = "早上好")
    @NotNull(message = "标题必填")
    private String title;

    @Schema(description = "消息内容", required = true, example = "你好呀")
    @NotNull(message = "消息内容不能为空")
    private String content;

    @Schema(description = "接收人邮箱", required = true, example = "460219753@qq.com")
    @NotNull(message = "接收人邮箱不能为空")
    private String receiveAccount;

    @Schema(description = "发送时间", required = true, example = "2002-03-22 12:12:12")
    @NotNull(message = "发送时间不能为空")
    private Date sendTime;
}
