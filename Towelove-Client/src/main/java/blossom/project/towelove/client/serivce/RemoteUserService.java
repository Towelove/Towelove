package blossom.project.towelove.client.serivce;

import blossom.project.towelove.client.fallback.RemoteUserFallbackFactory;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.mailaccount.MailAccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "towelove-user",
        fallbackFactory = RemoteUserFallbackFactory.class,
        contextId = "RemoteUserService")
public interface RemoteUserService {

    @GetMapping("")
    Result<MailAccountResponse>
    getMailAccount(@RequestParam("accountId")Long accountId);

}
