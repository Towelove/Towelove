package blossom.project.towelove.common.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.response.user
 * @className: CouplesInfoDto
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/4/23 15:59
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouplesInfoDto {
    private String coupleName;

    private String coupleAvatar;
}
