package blossom.project.towelove.common.request.user;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UpdateUserRequest {

    private String userName;

    private String nickName;

    private String smtpCode;

    private String phoneNumber;

    private String email;

    private String sex;

    private String avatar;

    private String password;
}
