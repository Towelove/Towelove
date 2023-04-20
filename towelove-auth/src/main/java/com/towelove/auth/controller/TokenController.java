package com.towelove.auth.controller;

import com.towelove.auth.form.LoginBody;
import com.towelove.auth.form.RegisterBody;
import com.towelove.auth.service.SysLoginService;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.mybatis.EncryptTypeHandler;
import com.towelove.common.core.utils.JwtUtils;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.common.redis.service.RedisService;
import com.towelove.common.security.auth.AuthUtil;
import com.towelove.common.security.service.TokenService;
import com.towelove.common.security.utils.SecurityUtils;
import com.towelove.system.api.domain.SysUser;
import com.towelove.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import cn.hutool.crypto.symmetric.*;
/**
 * @author: 张锦标
 * @date: 2023/2/24 9:49
 * Description:
 * 用户的登录，注册，页面信息刷新
 * 都由当前控制器层来完成
 * 当前控制器的请求都不会被网关拦截
 */
@RequestMapping("/auth")
@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysLoginService sysLoginService;
    /**
     * 登录操作用于获取token
     *
     * @param form 用户名和密码
     * @return 返回登录token
     */
    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form,HttpServletRequest request) {
        // 校验是否携带了token又进行登入
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotBlank(token)){
           return R.fail("用户已经登入,请勿重复登入");
        }
        // 用户登录
        LoginUser userInfo = sysLoginService.login(form.getUsername(),
                form.getPassword());
        //上面的代码只为LoginUser提供了SysUser对象，下面的createToken为其其他属性添加属性
        // 获取登录token
        //将token放入到请求头中
        return R.ok(tokenService.createToken(userInfo));
    }

    /**
     * 退出登录操作 将会删除redis中的token
     *
     * @param request 当前退出登录请求
     * @return
     */
    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request) {
        //token里面存储userkey username userid
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            sysLoginService.logout(username);
        }
        return R.ok();
    }

    /**
     * 刷新有效期 只要用户有点击操作就可以执行这个方法
     * 但是不要太频繁
     *
     * @param request
     * @return
     */
    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }

    /**
     * 用户注册操作
     * @param registerBody 注册信息
     * @return
     */
    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody) {
        // 用户注册
        sysLoginService.register(registerBody);
        return R.ok();
    }
    @Autowired
    private RedisService redisService;
    /**
     * 用户重置密码操作
     * 修改用户密码需要用到验证码
     * @param form 包含这次修改用户密码的信息
     * @return
     */
    @PutMapping("/resetPwd")
    public R<?> resetPassword(@RequestBody LoginBody form,HttpServletRequest request) {
        if (form.getOldPassword().equals(form.getNewPassword())){
            return R.fail("新旧密码不能一样");
        }
        sysLoginService.resetPwd(form);
        String token = request.getHeader("Authorization");
        String userKey =  "login_tokens:" + token;
        //删除修改用用户的redis缓存
        redisService.deleteObject(userKey);
        return R.ok(null,"修改密码成功");

    }
}
