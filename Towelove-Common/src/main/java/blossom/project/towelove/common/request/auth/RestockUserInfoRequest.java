package blossom.project.towelove.common.request.auth;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.request.auth
 * @className: RestockUserInfoRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2023/12/7 21:22
 * @version: 1.0
 */
@Data
public class RestockUserInfoRequest {

    private Long Id;

    @NotBlank(message = "nickName could not be null")
    private String nickName;


    @NotBlank(message = "sex could not be null")
    private String sex;


    private String email;


    private String phone;


    @NotBlank(message = "avatar could not be null")
    private String avatar;

    private String phoneVerifyCode;

    private String emailVerifyCode;
}
