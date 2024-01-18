package blossom.project.towelove.user.domain;

import blossom.project.towelove.common.domain.dto.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_user")
public class SysUser extends BaseEntity {

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
