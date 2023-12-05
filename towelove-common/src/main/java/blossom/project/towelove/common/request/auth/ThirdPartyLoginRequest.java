package blossom.project.towelove.common.request.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author 苏佳
 * @Date 2023 12 04 15 28
 **/
@Data
public class ThirdPartyLoginRequest {

    @NotBlank
    private String type;

    @NotBlank
    private String code;
}
