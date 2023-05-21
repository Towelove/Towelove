package com.towelove.system.api;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.towelove.common.core.constant.SecurityConstants;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.web.domain.AjaxResult;
import com.towelove.system.api.factory.SysUserFallbackFactory;
import com.towelove.system.api.domain.SysUser;
import com.towelove.system.api.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 张锦标
 * @date: 2023/2/23 18:51
 * Description:
 */
@FeignClient(
        contextId = "sysUserService",
        //value = ServiceNameConstants.SYSTEM_SERVICE,
        value = "towelove-system",
        fallbackFactory = SysUserFallbackFactory.class)
public interface RemoteSysUserService {
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source   请求来源
     * @return 结果
     */
    @GetMapping("/sys/user/info/{username}")
    public R<LoginUser> getUserInfo(@PathVariable("username") String username,
                                    @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 匹配旧密码是否正确
     * @param oldPassword
     * @return
     */
    @GetMapping("/sys/user/info/compare/pwd")
    public R<Boolean> comparePwd(@RequestParam("username") String username,
                                 @RequestParam("oldPassword") String oldPassword);
    /**
     * 注册用户信息
     *
     * @param sysUser 用户信息
     * @param source  请求来源
     * @return 结果
     */
    @PostMapping("/sys/user/info/register")
    public R<Boolean> registerUserInfo(@RequestBody SysUser sysUser,
                                       @RequestHeader(SecurityConstants.FROM_SOURCE)
                                       String source);

    @PutMapping("/sys/user/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser sysUser,
                               @RequestHeader(SecurityConstants.FROM_SOURCE)
                         String inner);
    @GetMapping("/sys/user/info/get/{id}")
    R<SysUser> getUserById(@PathVariable("id") Long userId);


}
