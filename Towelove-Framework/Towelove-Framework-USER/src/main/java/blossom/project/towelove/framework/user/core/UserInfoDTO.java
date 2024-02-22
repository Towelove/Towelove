package blossom.project.towelove.framework.user.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.framework.log.core
 * @className: UserInfoDTO
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/22 14:13
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDTO {
    private Long id;

    private String userName;

    private String nickName;

    private String sex;

    private String token;
}
