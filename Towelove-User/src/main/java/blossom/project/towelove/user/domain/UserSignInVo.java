package blossom.project.towelove.user.domain;

import lombok.Data;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.domain
 * @className: UserSignInVo
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/3 20:08
 * @version: 1.0
 */
@Data
public class UserSignInVo {
    /**
     *  签到情况：0101010101 形式
     */
    private String signInData;
    /**
     * 连续签到数
     */
    private Integer signInContinuous;
}
