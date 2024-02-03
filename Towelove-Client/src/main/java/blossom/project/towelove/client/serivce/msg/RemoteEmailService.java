package blossom.project.towelove.client.serivce.msg;

import blossom.project.towelove.client.fallback.RemoteEmailFallbackFactory;
import blossom.project.towelove.common.request.todoList.TodoRemindRequest;
import blossom.project.towelove.common.request.user.InvitedEmailRequest;
import blossom.project.towelove.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

/**
 * @author: ZhangBlossom
 * @date: 2024/1/25 15:36
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@FeignClient(value = "towelove-msg",
        fallbackFactory = RemoteEmailFallbackFactory.class,
        contextId = "RemoteEmailService")
public interface RemoteEmailService {

    /**
     * 提供发送验证码功能
     * @param email
     * @return
     */
    @GetMapping("/v1/email/code")
    Result<String> sendValidateCodeByEmail(@RequestParam("email") @NotBlank String email);


    /**
     * 用于提供发送邮件功能
     * @param request
     * @return
     */
    @PostMapping("/v1/email/remind")
    Result<String> todoRemindByEmail(@Validated @RequestBody TodoRemindRequest request);

    @PostMapping("/v1/email/invited")
    Result<String> sendInvitedEmail(@Validated @RequestBody InvitedEmailRequest request);


}
