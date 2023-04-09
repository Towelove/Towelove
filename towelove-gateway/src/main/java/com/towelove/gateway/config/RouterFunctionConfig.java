package com.towelove.gateway.config;

import com.towelove.gateway.handler.ValidateCodeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * @author: 张锦标
 * @date: 2023/3/10 13:42
 * RouterFunctionConfig类
 * 路由配置信息
 */
@Configuration
public class RouterFunctionConfig {
    @Autowired
    private ValidateCodeHandler validateCodeHandler;

    /**
     * 关于WebMVC的函数式编程
     * @see <a href="https://blog.csdn.net/asoklove/article/details/121327709">WebMVC.fn</a>
     * 不想要单独为某个方法生成一个Controller层的时候就可以怎么做
     */
    @SuppressWarnings("rawtypes")
    @Bean
    public RouterFunction routerFunction()
    {
        //传入的请求通过RouterFunction路由到处理程序函数
        return RouterFunctions.route(
                RequestPredicates.GET("/code")
                        .and(RequestPredicates
                                .accept(MediaType.TEXT_PLAIN)),
                validateCodeHandler);
    }
}
