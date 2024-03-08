package blossom.project.towelove.user.service;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.user.domain.InvitedCouplesRequest;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.service
 * @className: UserInvitedService
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/18 22:38
 * @version: 1.0
 */
public interface UserInvitedService {
    Result invited(InvitedCouplesRequest invitedCouplesRequest);

    String invitedByQrCode();
}
