package blossom.project.towelove.client.serivce;

import blossom.project.towelove.client.fallback.RemoteMsgFallbackFactory;
import blossom.project.towelove.client.fallback.RemoteUserFallbackFactory;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.msg.MsgTaskPageRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@FeignClient(value = "towelove-user",
        fallbackFactory = RemoteUserFallbackFactory.class,
        contextId = "RemoteUserService")
public interface RemoteUserService {



}
