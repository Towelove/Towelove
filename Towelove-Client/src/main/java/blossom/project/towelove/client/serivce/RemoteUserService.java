package blossom.project.towelove.client.serivce;

import blossom.project.towelove.client.fallback.RemoteUserFallbackFactory;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.domain.dto.ThirdPartyLoginUser;
import blossom.project.towelove.common.domain.dto.UserThirdParty;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import blossom.project.towelove.common.response.user.SysUserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@FeignClient(value = "towelove-user",
        fallbackFactory = RemoteUserFallbackFactory.class,
        contextId = "RemoteUserService")
public interface RemoteUserService {
    @PostMapping("/v1/user")
    Result<SysUser> saveUser(@RequestBody SysUser sysUser);

    @GetMapping("/v1/user")
    Result<SysUserVo> getUserById(@Valid @RequestParam("userId") @NotNull(message = "请求信息缺失") Long userId);

    @PostMapping("/v1/user/exist")
     Result<SysUser> findUserByPhoneOrEmail(@Valid @RequestBody AuthLoginRequest authLoginRequest);

    @GetMapping("/v1/user/permission")
     Result<List<SysUserPermissionDto>> getUserPermissionByUserId(@RequestParam("userId") Long userId);

    @GetMapping("/v1/user/thirdParty/exist")
    Result<Long> findUserIdByThirdPartyId(@RequestParam("socialUid") String socialUid);

    @PostMapping("v1/user/thirdParty")
    Result<String> saveThirdPartyUser(@RequestBody UserThirdParty userThirdParty);

    @PostMapping("/v1/user/thirdParty/access")
    Result<SysUser> accessByThirdPartyAccount(@RequestBody ThirdPartyLoginUser thirdPartyLoginUser);


}
