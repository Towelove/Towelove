package blossom.project.towelove.client.serivce;

import blossom.project.towelove.client.fallback.RemoteMsgFallbackFactory;
import blossom.project.towelove.client.fallback.RemoteUserFallbackFactory;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.msg.MsgTaskPageRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
import blossom.project.towelove.common.response.user.SysUserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@FeignClient(value = "towelove-user",
        fallbackFactory = RemoteUserFallbackFactory.class,
        contextId = "RemoteUserService")
public interface RemoteUserService {
    @PostMapping("")
    Result<String> saveUser(@RequestBody SysUser sysUser);

    @GetMapping("")
    Result<SysUserVo> getUserById(@Valid @RequestParam("userId") @NotNull(message = "请求信息缺失") Long userId);

    @PostMapping("/exist")
     Result<String> findUserByPhoneOrEmail(@Valid @RequestBody AuthLoginRequest authLoginRequest);
}
