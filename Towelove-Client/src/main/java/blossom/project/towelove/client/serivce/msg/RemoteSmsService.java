package blossom.project.towelove.client.serivce.msg;

import blossom.project.towelove.client.fallback.RemoteSmsFallbackFactory;
import blossom.project.towelove.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@FeignClient(value = "towelove-msg",
        fallbackFactory = RemoteSmsFallbackFactory.class,
        contextId = "RemoteSmsService")
public interface RemoteSmsService {


    @GetMapping("/v1/msg/sms")
    Result<String> sendValidateCodeByPhone(@NotBlank @RequestParam("phone")String phoneNumber);

}
