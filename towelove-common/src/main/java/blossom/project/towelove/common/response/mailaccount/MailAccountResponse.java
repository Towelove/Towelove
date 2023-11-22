package blossom.project.towelove.common.response.mailaccount;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

@Data
public class MailAccountResponse {

    @NotNull(message = "邮箱不能为空")
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
