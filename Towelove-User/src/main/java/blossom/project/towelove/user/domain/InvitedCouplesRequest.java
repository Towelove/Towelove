package blossom.project.towelove.user.domain;

import lombok.Data;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.domain
 * @className: InvitedCouplesRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/18 22:38
 * @version: 1.0
 */
@Data
public class InvitedCouplesRequest {
    /**
     * 被邀请方手机号
     */
    private String couplePhone;
    /**
     * 被邀请方邮箱
     */
    private String coupleEmail;



}
