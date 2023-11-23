package blossom.project.towelove.common.response.user;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class SysUserVo {

    private String userName;

    private String nickName;

    private String email;

    private String smtpCode;

    private String sex;

    private String avatar;
}
