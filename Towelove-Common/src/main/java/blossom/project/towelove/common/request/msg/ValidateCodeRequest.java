package blossom.project.towelove.common.request.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.msg.entity
 * @className: ValidateCodeRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/1 21:57
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidateCodeRequest {

    @NotBlank(message = "number could not be null")
    private String number;

    @NotBlank(message = "code could not be null")
    private String code;

    @NotBlank(message = "type could not be null")
    private String type;
}
