package blossom.project.towelove.common.request.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class AuthLoginRequest {

    private String phoneNumber;

    private String email;

    private String verifyCode;

    private String thirdPartyCode;
    /**
     * qq wx phone email
     */
    private String type;

}
