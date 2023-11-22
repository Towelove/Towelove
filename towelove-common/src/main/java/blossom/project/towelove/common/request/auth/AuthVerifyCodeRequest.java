package blossom.project.towelove.common.request.auth;

import lombok.Data;

@Data
public class AuthVerifyCodeRequest {
    private String phone;

    private String email;
}
