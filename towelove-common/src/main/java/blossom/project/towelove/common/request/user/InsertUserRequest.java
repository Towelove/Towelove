package blossom.project.towelove.common.request.user;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class InsertUserRequest {
    @NotBlank
    private String userName;

    private String nickName;

    @NotBlank
    private String password;

    private String email;

    private String smtpCode;

    private String phoneNumber;
    @NotBlank
    private String sex;

    private String avatar;

    private String loginIp;
}
