package com.towelove.common.security.interceptor;


import com.towelove.common.core.constant.SecurityConstants;
import com.towelove.common.core.context.SecurityContextHolder;
import com.towelove.common.core.utils.ServletUtils;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.common.security.auth.AuthUtil;
import com.towelove.common.security.utils.SecurityUtils;
import com.towelove.system.api.model.LoginUser;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 自定义请求头拦截器，将Header数据封装到线程变量中方便获取
 * 注意：此拦截器会同时验证当前用户有效期自动刷新有效期
 *
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 */
public class HeaderInterceptor implements AsyncHandlerInterceptor
{
    /**
     *
     * @param request 当前请求对象
     * @param response 请求响应对象
     * @param handler 当前请求的方法
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception
    {
        if (!(handler instanceof HandlerMethod))
        {
            return true;
        }
        System.out.println("------开始设定SecurityContextHolder参数-----");
        SecurityContextHolder.setUserId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USER_ID));
        SecurityContextHolder.setUserName(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USERNAME));
        SecurityContextHolder.setUserKey(ServletUtils.getHeader(request, SecurityConstants.USER_KEY));

        String token = SecurityUtils.getToken();
        if (StringUtils.isNotEmpty(token))
        {
            LoginUser loginUser = AuthUtil.getLoginUser(token);
            if (Objects.nonNull(loginUser))
            {
                AuthUtil.verifyLoginUserExpire(loginUser);
                SecurityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception
    {
        SecurityContextHolder.remove();
    }
}
