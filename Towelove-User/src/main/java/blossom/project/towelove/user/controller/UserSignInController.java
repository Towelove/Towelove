package blossom.project.towelove.user.controller;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.user.domain.UserSignInVo;
import blossom.project.towelove.user.service.UserSignRecordService;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
@RequestMapping("/v1/sign-in")
public class UserSignInController {

    private final UserSignRecordService userSignRecordService;
    /**
     * 用户签到
     * @param
     * @return
     */
    @PostMapping()
    public Result<String> signInByUserId(){
        return Result.ok(userSignRecordService.singnInByUserId());
    }

    /**
     * 获取用户签到总天数
     * @param
     * @return
     */
    @GetMapping()
    public Result<Long> getTotalSignIn(){
        return Result.ok(userSignRecordService.getSignInTotally());
    }

    /**
     * 获取用户本月签到总天数
     * @param
     * @return
     */
    @GetMapping("/month")
    public Result<UserSignInVo> getTotalSignInByMouth(@RequestParam("date") String date){
        LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return Result.ok(userSignRecordService.getSignInByMouthTotally(localDateTime));
    }




}
