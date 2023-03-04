package com.towelove.auth.controller;

import com.towelove.auth.form.LoginBody;
import com.towelove.auth.form.RegisterBody;
import com.towelove.auth.service.SysLoginService;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.utils.JwtUtils;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.common.security.auth.AuthUtil;
import com.towelove.common.security.service.TokenService;
import com.towelove.common.security.utils.SecurityUtils;
import com.towelove.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form)
    {
        // 用户登录
        LoginUser userInfo = sysLoginService.login(form.getUsername(), form.getPassword());
        // 获取登录token
        //将token放入到请求头中
        return R.ok(tokenService.createToken(userInfo));
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request)
    {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            sysLoginService.logout(username);
        }
        return R.ok();
    }

    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }
    //TODO 注册可以直接写完整一点
    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody)
    {
        // 用户注册
        sysLoginService.register(registerBody.getUsername(), registerBody.getPassword());
        return R.ok();
    }
}
