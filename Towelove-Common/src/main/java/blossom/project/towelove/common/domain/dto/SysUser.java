package blossom.project.towelove.common.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class SysUser extends BaseEntity {
    @TableField("id")
    private Long id;


    @TableField("user_name")
    private String userName;

    @TableField("nick_name")
    private String nickName;


    @TableField("email")
    private String email;

    @TableField("smtp_code")
    private String smtpCode;

    @TableField("phonenumber")
    private String phoneNumber;

    @TableField("sex")
    private String sex;

    @TableField("avatar")
    private String avatar;

    @TableField("login_ip")
    private String loginIp;

}
