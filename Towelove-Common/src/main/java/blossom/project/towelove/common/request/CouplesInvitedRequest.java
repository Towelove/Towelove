package blossom.project.towelove.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.request
 * @className: CouplesInvitedRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 15:42
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouplesInvitedRequest {
    @NotBlank
    private String invitedCode;

}
