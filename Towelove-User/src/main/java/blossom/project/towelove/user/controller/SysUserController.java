package blossom.project.towelove.user.controller;

import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.RestockUserInfoDTO;
import blossom.project.towelove.common.request.auth.RestockUserInfoRequest;
import blossom.project.towelove.common.request.user.InsertUserRequest;
import blossom.project.towelove.common.request.user.UpdateUserRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.LoginUserResponse;
import blossom.project.towelove.common.response.user.SysUserDTO;
import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import blossom.project.towelove.common.response.user.SysUserVo;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.user.entity.SysUser;
import blossom.project.towelove.user.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

@LoveLog
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class SysUserController {


    private final SysUserService sysUserService;

    /**
     * 插入用户信息，注册接口调用
     * @param userRequest
     * @return
     */
    @PostMapping("")
    public Result<SysUser> saveUser(@RequestBody InsertUserRequest userRequest) {
        return Result.ok(sysUserService.inserUser(userRequest));
    }

    /**
     * 分页查询用户数据 需要管理员权限
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<SysUserVo>> getUserPage(@Validated @RequestParam("pageNo") @NotNull(message = "分页参数缺失") Integer pageNo
            , @RequestParam("pageSize") @NotNull(message = "分页参数缺失") Integer pageSize) {
        return Result.ok(sysUserService.selectByPage(pageNo, pageSize));
    }

    /**
     * 根据手机号或者邮箱判断用户是否存在
     * @param authLoginRequest
     * @return
     */
    @PostMapping("/exist")
    public Result<LoginUserResponse> findUserByPhoneOrEmail(@Validated @RequestBody AuthLoginRequest authLoginRequest) {
        return Result.ok(sysUserService.findUser(authLoginRequest));
    }


    /**
     * 获得用户信息
     * @param
     * @return
     */
    @GetMapping("")
    public Result<SysUserDTO> getUserById() {
        return Result.ok(sysUserService.selectByUserId());
    }

    /**
     * 更新用户信息
     * @param request
     * @param
     * @return
     */
    @PutMapping("")
    public Result<String> updateUserById(@RequestBody UpdateUserRequest request) {
        return Result.ok(sysUserService.updateUser(request));
    }

    /**
     * 注销用户
     * @param userId
     * @param httpServletRequest
     * @return
     */
    @DeleteMapping("")
    public Result<String> deleteUserById(@Validated @RequestParam("userId") @NotNull(message = "请求信息缺失") Long userId, HttpServletRequest httpServletRequest) {
        return Result.ok(sysUserService.deleteById(userId, httpServletRequest));
    }


    @PutMapping("/restock")
    public Result<LoginUserResponse> restockUserInfo(@RequestBody RestockUserInfoDTO restockUserInfoDTO){
        return Result.ok(sysUserService.restockUserInfo(restockUserInfoDTO));
    }

    @GetMapping("/findByEmailOrPhone")
    public Result<Boolean> findByEmailOrPhone(@RequestParam(value = "phone",required = false) String phone,
                                              @RequestParam(value = "email",required = false) String email){
        return Result.ok(sysUserService.findByPhoneOrEmail(phone,email));
    }


}
