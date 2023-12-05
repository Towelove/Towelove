package blossom.project.towelove.user.controller;

/**
 * @Author SIK
 * @Date 2023 12 05 11 29
 **/

import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.user.domain.UserThirdParty;
import blossom.project.towelove.user.service.UserThirdPartyService;
import blossom.project.towelove.common.response.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@LoveLog
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/user/thirdParty")
public class UserThirdPartyController {

    private final UserThirdPartyService userThirdPartyService;

    // 添加第三方用户信息
    @PostMapping("")
    public Result<?> addThirdPartyUser(@RequestBody UserThirdParty userThirdParty) {
        boolean saved = userThirdPartyService.save(userThirdParty);
        return saved ? Result.ok() : Result.fail("保存第三方用户信息失败");
    }

    // 根据用户ID查询所有关联的第三方登录信息
    @GetMapping("/find")
    public Result<?> findThirdPartyAccountsByUserId(@RequestParam Long userId) {
        List<UserThirdParty> thirdPartyAccounts = userThirdPartyService.getByUserId(userId);
        return Result.ok(thirdPartyAccounts);
    }

    @GetMapping("")
    public Result<?> findUserIdByThirdPartyId(@RequestParam String socialUid) {
        return Result.ok(userThirdPartyService.getByThirdPartyId(socialUid).getUserId());
    }
}

