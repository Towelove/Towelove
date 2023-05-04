package com.towelove.common.security.config;

import com.towelove.common.security.interceptor.HeaderInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * 拦截器配置
 *
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 */
public class WebMvcConfig implements WebMvcConfigurer
{
    /** 不需要拦截地址 */
    public static final String[] excludeUrls = { "/auth/login",
            "/auth/logout", "/auth/refresh","/auth/register" };

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(getHeaderInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls)
                .order(-10);
    }

    /**
     * 自定义请求头拦截器
     */
    public HeaderInterceptor getHeaderInterceptor()
    {
        return new HeaderInterceptor();
    }

    ///**
    // * 创建 CorsFilter Bean，解决跨域问题
    // */
    //@Bean
    //public FilterRegistrationBean<CorsFilter> corsFilterBean() {
    //    // 创建 CorsConfiguration 对象
    //    CorsConfiguration config = new CorsConfiguration();
    //    config.setAllowCredentials(true);
    //    config.addAllowedOriginPattern("*"); // 设置访问源地址
    //    config.addAllowedHeader("*"); // 设置访问源请求头
    //    config.addAllowedMethod("*"); // 设置访问源请求方法
    //    // 创建 UrlBasedCorsConfigurationSource 对象
    //    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //    source.registerCorsConfiguration("/**", config); // 对接口配置跨域设置
    //    return createFilterBean(new CorsFilter(source), -2147483648);
    //}
    //private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
    //    FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
    //    bean.setOrder(order);
    //    return bean;
    //}
    ///**
    // * 开启跨域
    // */
    //@Override
    //public void addCorsMappings(CorsRegistry registry) {
    //    // 设置允许跨域的路由
    //    registry.addMapping("/**")
    //            // 设置允许跨域请求的域名
    //            .allowedOrigins("*")
    //            // 设置允许的方法
    //            .allowedMethods("GET")
    //            .allowedMethods("POST")
    //            .allowedMethods("DELETE")
    //            .allowedMethods("PUT");
    //}
}
