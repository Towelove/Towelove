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

    @NotBlank
    private String nickName;

    @NotBlank
    private String password;

    @NotBlank
    private String sex;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String avatar;

    @NotBlank
    private String phoneVerifyCode;
    @NotBlank
    private String emailVerifyCode;
}
