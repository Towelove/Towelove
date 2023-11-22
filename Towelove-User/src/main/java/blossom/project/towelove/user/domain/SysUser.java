package blossom.project.towelove.user.domain;

import blossom.project.towelove.framework.mysql.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SysUser extends BaseEntity {
    @TableField("id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    @TableField("user_name")
    private String userName;

    @TableField("nick_name")
    private String nickName;

    @TableField("password")
    private String password;

    @TableField("email")
    public String email;

    @TableField("smtp_code")
    private String smtpCode;

    @TableField("phonenumber")
    public String phoneNumber;

    @TableField("sex")
    private String sex;

    @TableField("avatar")
    private String avatar;

    @TableField("login_ip")
    private String loginIp;

}
