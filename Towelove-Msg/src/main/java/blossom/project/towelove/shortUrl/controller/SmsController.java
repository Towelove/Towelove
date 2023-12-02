package blossom.project.towelove.shortUrl.controller;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.shortUrl.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/22 21:07
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * SmsController类
 */
@LoveLog
@RestController
@RequestMapping("/v1/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @GetMapping("")
    public Result<String> sendValidateCode(@NotBlank @RequestParam("phone")String phoneNumber){
        return Result.ok(smsService.sendValidateCode(phoneNumber));
    }
}
