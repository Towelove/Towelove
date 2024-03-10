package blossom.project.towelove.client.serivce.user;

import blossom.project.towelove.client.config.FeignMarkInterceptor;
import blossom.project.towelove.client.fallback.RemoteUserFallbackFactory;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.domain.dto.ThirdPartyLoginUser;
import blossom.project.towelove.common.domain.dto.UserThirdParty;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.RestockUserInfoDTO;
import blossom.project.towelove.common.request.auth.RestockUserInfoRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import blossom.project.towelove.common.response.user.SysUserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@FeignClient(value = "towelove-user",
        fallbackFactory = RemoteUserFallbackFactory.class,
        contextId = "RemoteUserService",
        configuration = FeignMarkInterceptor.class)
public interface RemoteUserService {
    @PostMapping("/v1/user/sys")
    Result<SysUser> saveUser(@RequestBody SysUser sysUser);

    @GetMapping("/v1/user/sys")
    Result<SysUserVo> getUserById(@Valid @RequestParam("userId") @NotNull(message = "请求信息缺失") Long userId);

    @PostMapping("/v1/user/sys/exist")
    Result<SysUser> findUserByPhoneOrEmail(@Valid @RequestBody AuthLoginRequest authLoginRequest);

    @GetMapping("/v1/user/sys/permission")
    Result<SysUserPermissionDto> getUserPermissionByUserId(@RequestParam("userId") Long userId);

    @GetMapping("/v1/user/sys/thirdParty/exist")
    Result<Long> findUserIdByThirdPartyId(@RequestParam("socialUid") String socialUid);

    @PostMapping("v1/user/sys/thirdParty")
    Result<String> saveThirdPartyUser(@RequestBody UserThirdParty userThirdParty);

    @PostMapping("/v1/user/sys/thirdParty/access")
    Result<SysUser> accessByThirdPartyAccount(@RequestBody ThirdPartyLoginUser thirdPartyLoginUser);

    @PutMapping("/v1/user/sys/restock")
    Result<SysUser> restockUserInfo(@RequestBody RestockUserInfoDTO restockUserInfoDTO);

    @GetMapping("/v1/user/sys/findByEmailOrPhone")
    Result<Boolean> findByEmailOrPhone(@RequestParam(value = "phone",required = false) String phone
            , @RequestParam(value = "email",required = false) String email);

}
