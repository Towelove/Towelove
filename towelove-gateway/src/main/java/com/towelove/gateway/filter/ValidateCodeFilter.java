//package com.towelove.gateway.filter;
//
//import com.alibaba.fastjson2.JSON;
//import com.alibaba.fastjson2.JSONObject;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.towelove.common.core.utils.ServletUtils;
//import com.towelove.common.core.utils.StringUtils;
//import com.towelove.gateway.config.properties.CaptchaProperties;
//import com.towelove.gateway.service.ValidateCodeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.nio.CharBuffer;
//import java.nio.charset.StandardCharsets;
//import java.util.concurrent.atomic.AtomicReference;
//
///**
// * @author: 张锦标
// * @date: 2023/3/10 13:42
// * ValidateCodeFilter类
// * 验证码校验过滤器
// */
////@Component
//public class ValidateCodeFilter //implements GlobalFilter, Ordered
//        extends AbstractGatewayFilterFactory<Object>
//{
//    //需要生成验证码的路径
//    private final static String[] VALIDATE_URL =
//            new String[] { "/auth/login", "/auth/register" };
//    //验证码服务
//    @Autowired
//    private ValidateCodeService validateCodeService;
//    //验证码配置学习
//    @Autowired
//    private CaptchaProperties captchaProperties;
//    //验证码内容
//    private static final String CODE = "code";
//    //验证码的uuid
//    private static final String UUID = "uuid";
//
//    @Override
//    public GatewayFilter apply(Object config)
//    {
//         return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//
//            // 非登录/注册请求或验证码关闭，不处理
//            if (!StringUtils.containsAnyIgnoreCase(request.getURI().getPath(),
//                    VALIDATE_URL) || !captchaProperties.getEnabled())
//            {
//                return chain.filter(exchange);
//            }
//
//            try
//            {
//                String rspStr = resolveBodyFromRequest(request);
//                //接收JSON格式的请求
//                JSONObject obj = JSON.parseObject(rspStr);
//                validateCodeService.checkCaptcha(obj.getString(CODE), obj.getString(UUID));
//            }
//            catch (Exception e)
//            {
//                return ServletUtils.webFluxResponseWriter(exchange.getResponse(), e.getMessage());
//            }
//            return chain.filter(exchange);
//        };
//    }
//
//    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest)
//    {
//        // 获取请求体
//        Flux<DataBuffer> body = serverHttpRequest.getBody();
//        AtomicReference<String> bodyRef = new AtomicReference<>();
//        body.subscribe(buffer -> {
//            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
//            DataBufferUtils.release(buffer);
//            bodyRef.set(charBuffer.toString());
//        });
//        return bodyRef.get();
//    }
//
//    //@Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//
//        // 非登录/注册请求或验证码关闭，不处理
//        if (!StringUtils.containsAnyIgnoreCase(request.getURI().getPath(),
//                VALIDATE_URL) || !captchaProperties.getEnabled())
//        {
//            return chain.filter(exchange);
//        }
//
//        try
//        {
//            String rspStr = resolveBodyFromRequest(request);
//            JSONObject obj = JSON.parseObject(rspStr);
//            validateCodeService.checkCaptcha(obj.getString(CODE), obj.getString(UUID));
//        }
//        catch (Exception e)
//        {
//            return ServletUtils.webFluxResponseWriter(exchange.getResponse(), e.getMessage());
//        }
//        return chain.filter(exchange);
//    }
//
//    //@Override
//    public int getOrder() {
//        return 0;
//    }
//}
