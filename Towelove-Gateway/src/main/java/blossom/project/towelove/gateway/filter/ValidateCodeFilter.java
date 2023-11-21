package blossom.project.towelove.gateway.filter;

import blossom.project.towelove.common.utils.ServletUtils;
import blossom.project.towelove.common.utils.StringUtils;
import blossom.project.towelove.gateway.service.ValidateCodeService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import blossom.project.towelove.gateway.config.properties.KaptchaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: 张锦标
 * @date: 2023/3/10 13:42
 * ValidateCodeFilter类
 * 验证码校验过滤器
 * 这个类的问题在于会不断的导致gateway请求重放
 */
//@Component
@Deprecated
public class ValidateCodeFilter
        //extends OncePerRequestFilter
        //implements GlobalFilter, Ordered
        extends AbstractGatewayFilterFactory<Object> {

    private static final Logger log = LoggerFactory.getLogger(ValidateCodeFilter.class);

    //需要生成验证码的路径
    private final static String[] VALIDATE_URL = new String[]{"/auth/login", "/auth/register"};
    //验证码服务
    @Autowired
    private ValidateCodeService validateCodeService;
    //验证码配置学习
    @Autowired
    private KaptchaProperties kaptchaProperties;
    //验证码内容
    private static final String CODE = "code";
    //验证码的uuid
    private static final String UUID = "uuid";

    //实现局部过滤器
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            //从Header中获取用户信息
            log.info("请求ID：{}", request.getId());
            String Key = "Request:ID:" + request.getId();

            //if (request.getURI().getPath().matches(".+.ico$")){
            //    return chain.filter(exchange);
            //}

            // 非登录/注册请求或验证码关闭，不处理
            if (!StringUtils.containsAnyIgnoreCase(request.getURI().getPath(), VALIDATE_URL) || !kaptchaProperties.getEnabled()) {
                return chain.filter(exchange);
            }
            //if (StringUtils.containsAnyIgnoreCase(request.getURI().getPath(), VALIDATE_URL) || !kaptchaProperties
            // .getEnabled()) {
            //    ServerHttpRequest.Builder mutate = request.mutate();
            //    return chain.filter(exchange.mutate()
            //            .request(mutate.build()).build());
            //}

            try {
                //获取请求体内容
                String rspStr = resolveBodyFromRequest(request);
                //接收JSON格式的请求
                JSONObject obj = JSON.parseObject(rspStr);
                //校验验证码是否正确
                validateCodeService.checkCaptcha(obj.getString(CODE), obj.getString(UUID));


            } catch (Exception e) {
                return ServletUtils.webFluxResponseWriter
                        (exchange.getResponse(), e.getMessage());
            }
            return chain.filter(exchange);
        };
    }

    /**
     * 获取请求体内容并且转换为字符串
     *
     * @param serverHttpRequest 请求
     * @return
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        // 获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        return bodyRef.get();
    }

    // ----------- 全局过滤器使用的 ------------------
    //@Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 非登录/注册请求或验证码关闭，不处理
        if (!StringUtils.containsAnyIgnoreCase(request.getURI().getPath(), VALIDATE_URL) || !kaptchaProperties.getEnabled()) {
            return chain.filter(exchange);
        }

        try {
            String rspStr = resolveBodyFromRequest(request);
            JSONObject obj = JSON.parseObject(rspStr);
            validateCodeService.checkCaptcha(obj.getString(CODE), obj.getString(UUID));
        } catch (Exception e) {
            return ServletUtils.webFluxResponseWriter(exchange.getResponse(), e.getMessage());
        }
        return chain.filter(exchange);
    }

    //@Override
    public int getOrder() {
        return -100;
    }

    //@Override
    //protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain
    // filterChain) throws ServletException, IOException {
    //
    //}
}
