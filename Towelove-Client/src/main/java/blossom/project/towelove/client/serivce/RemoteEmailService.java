package blossom.project.towelove.client.serivce;

import blossom.project.towelove.client.fallback.RemoteEmailFallbackFactory;
import blossom.project.towelove.client.fallback.RemoteMsgFallbackFactory;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.msg.MsgTaskPageRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@FeignClient(value = "towelove-email",
        fallbackFactory = RemoteEmailFallbackFactory.class,
        contextId = "RemoteEmailService")
public interface RemoteEmailService {

    /**
     * 生成验证码
     *
     * @param email
     * @return
     */
    @GetMapping("")
    Result<String>
    sendValidateCode(@NotBlank @RequestParam(name = "email")String email);

}
