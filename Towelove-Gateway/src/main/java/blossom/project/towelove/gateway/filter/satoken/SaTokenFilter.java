package blossom.project.towelove.gateway.filter.satoken;

import blossom.project.towelove.common.constant.SecurityConstant;
import blossom.project.towelove.common.response.AjaxResult;
import blossom.project.towelove.common.response.Result;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.BackResultException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;
@Configuration
public class SaTokenFilter {
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")    /* 拦截全部path */
                // 开放地址
                .addExclude("/favicon.ico")
                .addExclude("/v1/")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录校验 -- 拦截所有路由，并排除/auth/** 用于开放登录
                    SaRouter.match("/**", "/v1/auth/**", r -> {
                        try {
                            StpUtil.checkLogin();
                        }
                        catch (Exception e){
                            throw new BackResultException(JSON.toJSONString(Result.fail("token无效,请重新登入",HttpStatus.UNAUTHORIZED.value(),"token无效,请重新登入",null)));
                        }
                    });
                    // 权限认证 -- 不同模块, 校验不同权限
                    SaRouter.match("/v1/**","/v1/auth/**", r -> StpUtil.checkPermission("user"));
//                    SaRouter.match("/server/**", r -> StpUtil.checkPermission("user"));
//                    SaRouter.match("/lover/**", r -> StpUtil.checkPermission("user"));
                    SaRouter.match("/admin/**", "/v1/auth/**",r -> StpUtil.checkPermission("admin"));

//                  SaRouter.match("/orders/**", r -> StpUtil.checkPermission("orders"));
// 更多匹配 ...  */
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(e -> AjaxResult.error(HttpStatus.FORBIDDEN.value(),e.getMessage()));
    }

    /**
     * 由于网关没有引入springMVC依赖，所以使用feign的时候需要手动装配messageConverters
     * @param converters
     * @return
     */
    @Bean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}
