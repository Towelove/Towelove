package blossom.project.towelove.user.controller;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.user.service.UserSignRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.controller
 * @className: UserSignInController
 * @author: Link Ji
 * @description:
 * @date: 2023/11/27 23:30
 * @version: 1.0
 */
@RestController
@LoveLog
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserSignInController {

    private final UserSignRecordService userSignRecordService;
    /**
     * 用户签到
     * @param userId
     * @return
     */
    @PostMapping("/sign-in")
    public Result<String> signInByUserId(@RequestParam("userId") Long userId){
        return Result.ok(userSignRecordService.singnInByUserId(userId));
    }

    /**
     * 获取用户签到总天数
     * @param userId
     * @return
     */
    @GetMapping("/sign-in")
    public Result<Long> getTotalSignIn(@RequestParam("userId") Long userId){
        return Result.ok(userSignRecordService.getSignInTotally(userId));
    }

    /**
     * 获取用户本月签到总天数
     * @param userId
     * @return
     */
    @GetMapping("/sign-in-mouth")
    public Result<Long> getTotalSignInByMouth(@RequestParam("userId") Long userId){
        return Result.ok(userSignRecordService.getSignInByMouthTotally(userId));
    }


    //TODO
    /**
     * 当前方法用于通过传递日期的方式，获得当前月份的签到情况
     * 写的简单一点，只能看当前月的即可，之前月的就可以冷热分离放到冷库了
     * @return
     */
    @GetMapping("/sign-in/{xxx}/{xxxx}/{xxxxx}")
    public Result<String> getSignInMonth(){
        return null;
        //return Result.ok(userSignRecordService.getSignInMonth());
    }
}
