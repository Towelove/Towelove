package blossom.project.towelove.user.controller;

import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.user.InsertUserRequest;
import blossom.project.towelove.common.request.user.UpdateUserRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.SysUserVo;
import blossom.project.towelove.user.domain.SysUser;
import blossom.project.towelove.user.mapper.SysUserMapper;
import blossom.project.towelove.user.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class SysUserController {

    private final SysUserService sysUserService;

    @PostMapping("")
    public Result<String> saveUser(@RequestBody InsertUserRequest userRequest) {
        return Result.ok(sysUserService.inserUser(userRequest));
    }

    @GetMapping("/page")
    public Result<PageResponse<SysUserVo>> getUserPage(@Validated @RequestParam("pageNo") @NotNull(message = "分页参数缺失") Integer pageNo
            , @RequestParam("pageSize") @NotNull(message = "分页参数缺失") Integer pageSize) {
        return Result.ok(sysUserService.selectByPage(pageNo, pageSize));
    }

    @PostMapping("/exist")
    public Result<String> findUserByPhoneOrEmail(@Validated @RequestBody AuthLoginRequest authLoginRequest) {
        return Result.ok(sysUserService.findUser(authLoginRequest));
    }

    @GetMapping("")
    public Result<SysUserVo> getUserById(@Validated @RequestParam("userId") @NotNull(message = "请求信息缺失") Long userId) {
        return Result.ok(sysUserService.selectByUserId(userId));
    }

    @PutMapping("")
    public Result<String> updateUserById(@RequestBody UpdateUserRequest request, HttpServletRequest httpServletRequest) {
        return Result.ok(sysUserService.updateUser(request, httpServletRequest));
    }

    @DeleteMapping("")
    public Result<String> deleteUserById(@Validated @RequestParam("userId") @NotNull(message = "请求信息缺失") Long userId, HttpServletRequest httpServletRequest) {
        return Result.ok(sysUserService.deleteById(userId, httpServletRequest));
    }
}
