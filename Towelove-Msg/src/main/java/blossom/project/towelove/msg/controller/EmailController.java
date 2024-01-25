package blossom.project.towelove.msg.controller;

import blossom.project.towelove.common.request.todoList.TodoRemindRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.msg.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/22 20:17
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * EmailController类
 */

@LoveLog
@RestController
@RequestMapping("/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/code")
    public Result<String> sendValidateCodeByEmail(@RequestParam("email") @NotBlank String email){
        return Result.ok(emailService.generateAndSendValidateCode(email));
    }

    @PostMapping("/remind")
    public Result<String> todoRemindByEmail(@Validated @RequestBody TodoRemindRequest request){
        return Result.ok(emailService.todoRemind(request));
    }



}
