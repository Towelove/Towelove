package blossom.project.towelove.common.response.user;

import lombok.Data;

@Data
public class SysUserDTO {

    private String userName;

    private String nickName;

    private String email;

    private String smtpCode;


    private String sex;


    private String phoneNumber;


    private String avatar;

    private Long coupleId;

    private Long boyId;

    private Long girlId;
}
