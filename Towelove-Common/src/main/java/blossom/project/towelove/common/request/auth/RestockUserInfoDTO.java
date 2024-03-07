package blossom.project.towelove.common.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.request.auth
 * @className: RestockUserInfoDTO
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/7 23:20
 * @version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestockUserInfoDTO {
    private Long Id;

    @NotBlank(message = "nickName could not be null")
    private String nickName;


    @NotBlank(message = "sex could not be null")
    private String sex;


    private String email;


    private String phoneNumber;


    @NotBlank(message = "avatar could not be null")
    private String avatar;

}
