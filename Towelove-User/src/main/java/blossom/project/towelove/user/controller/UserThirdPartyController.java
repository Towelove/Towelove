package blossom.project.towelove.user.controller;

/**
 * @Author SIK
 * @Date 2023 12 05 11 29
 **/

import blossom.project.towelove.common.domain.dto.ThirdPartyLoginUser;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.user.entity.SysUser;
import blossom.project.towelove.user.domain.UserThirdParty;
import blossom.project.towelove.user.service.UserThirdPartyService;
import blossom.project.towelove.common.response.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@LoveLog
@RestController
@RequiredArgsConstructor
@RequestMapping("/third/party")
public class UserThirdPartyController {

    private final UserThirdPartyService userThirdPartyService;

    /**
     * 保存第三方用户信息
     * @param userThirdParty
     * @return
     */
    @PostMapping("")
    public Result<?> saveThirdPartyUser(@RequestBody UserThirdParty userThirdParty) {
        boolean saved = userThirdPartyService.save(userThirdParty);
        return saved ? Result.ok() : Result.fail("保存第三方用户信息失败");
    }

    /**
     * 根据第三方ID查询用户ID
     * @param socialUid
     * @return
     */
    @GetMapping("/exist")
    public Result<?> findUserIdByThirdPartyId(@RequestParam String socialUid) {
        return Result.ok(userThirdPartyService.getByThirdPartyId(socialUid).getUserId());
    }

    /**
     * 根据用户ID查询第三方账户信息
     * @param userId
     * @return
     */
    @GetMapping("/find")
    public Result<?> findThirdPartyAccountsByUserId(@RequestParam Long userId) {
        List<UserThirdParty> thirdPartyAccounts = userThirdPartyService.getByUserId(userId);
        return Result.ok(thirdPartyAccounts);
    }

    @PostMapping("/access")
    public Result<SysUser> accessByThirdPartyAccount(@RequestBody ThirdPartyLoginUser thirdPartyLoginUser){
        return Result.ok(userThirdPartyService.accessByThirdPartyAccount(thirdPartyLoginUser));
    }


}

