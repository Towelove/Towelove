package blossom.project.towelove.msg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author: 张锦标
 * @date: 2023/3/16 9:12
 * CompletedMailMsgTask
 * 完成邮件消息任务，包含了一条邮件发送时所需要的所有信息
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CompletedMailMsgTask extends MsgTask {

    //---------邮箱账号内容-------------
    @NotNull(message = "发件人邮箱不能为空")
    @Email(message = "必须是 Email 格式")
    private String mail;

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码必填")
    private String password;

    @NotNull(message = "SMTP 服务器域名不能为空")
    private String host;

    @NotNull(message = "SMTP 服务器端口不能为空")
    private Integer port;

    @NotNull(message = "是否开启 ssl 必填")
    private Boolean sslEnable;
}
