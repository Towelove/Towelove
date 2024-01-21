package blossom.project.towelove.user.controller;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.user.domain.InvitedCouplesRequest;
import blossom.project.towelove.user.service.UserInvitedService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.controller
 * @className: UserInvitedController
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/18 22:35
 * @version: 1.0
 */
@RestController
@RequestMapping("/v1/invited")
@RequiredArgsConstructor
public class UserInvitedController {

    private final UserInvitedService userInvitedService;

    /**
     * 生成邀请码，发送邀请信息or邮件
     * @param invitedCouplesRequest
     * @return
     */
    @PostMapping("")
    public Result invitedCouples(@Validated @RequestBody InvitedCouplesRequest invitedCouplesRequest){
        return userInvitedService.invited(userInvitedService);
    }

}
