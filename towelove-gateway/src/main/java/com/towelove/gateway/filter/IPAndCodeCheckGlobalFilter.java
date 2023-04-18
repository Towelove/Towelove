package com.towelove.gateway.filter;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.towelove.common.core.constant.RedisServiceConstants;
import com.towelove.common.core.utils.ServletUtils;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.common.redis.service.RedisService;
import com.towelove.gateway.config.properties.KaptchaProperties;
import com.towelove.gateway.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: 张锦标
 * @date: 2023/3/22 19:56
 * 网关里面 过滤器
 * ip拦截
 * 请求都有一个源头
 * 电话 144  027  010
 * 请求------->gateway------->service
 * 黑名单 black_list
 * 白名单 white_list
 * 根据数量
 * 像具体的业务服务 一般黑名单
 * 一般像数据库 用白名单
 * <p>
 * 当前过滤器用于过滤验证码和ip黑名单
 */
@Component
public class IPAndCodeCheckGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 网关的并发比较高 不要再网关里面直接操作mysql
     * 后台系统可以查询数据库 用户量 并发量不大
     * 如果并发量大 可以查redis 或者 在内存中写好
     */

    @Resource
    private RedisService redisService;
    //初始化黑名单
    public static Set<Object> BLACK_LIST;

    //需要生成验证码的路径
    private final static String[] VALIDATE_URL =
            new String[]{"/auth/login", "/auth/register", "/auth/resetPwd"};
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

    @Autowired
    @Qualifier("cpuThreadPool")
    private ThreadPoolExecutor threadPoolExecutor;

    @PostConstruct
    public void initBlackList() {
        //BLACK_LIST = redisService.getCacheSet(RedisServiceConstants.BLACK_LIST_IP);
        threadPoolExecutor.execute(() -> {
            //每十秒钟刷新一次黑名单
            while (true) {
                try {
                    BLACK_LIST = redisService.getCacheSet
                            (RedisServiceConstants.BLACK_LIST_IP);
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    /**
     * 1.拿到ip
     * 2.校验ip是否符合规范
     * 3.放行/拦截
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String ip = request.getHeaders().getHost().getHostString();
        System.out.println("-------当前访问ip为：" + ip + "---------");
        // 查询数据库 看这个ip是否存在黑名单里面   mysql数据库的并发
        // 只要是能存储数据地方都叫数据库 redis  mysql
        if (!BLACK_LIST.contains(ip)) {

            // 非登录/注册请求或验证码关闭，不处理
            if (!StringUtils.containsAnyIgnoreCase(request.getURI().getPath(), VALIDATE_URL) || !kaptchaProperties.getEnabled()) {
                return chain.filter(exchange);
            }

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
        }

        // 拦截
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("content-type", "application/json;charset=utf-8");
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("code", 438);
        map.put("msg", "你已被加入黑名单，如需申请解除，可以私信WX:15377920718");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = new byte[0];
        try {
            bytes = objectMapper.writeValueAsBytes(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        DataBuffer wrap = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(wrap));
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

    @Override
    public int getOrder() {
        return -199;
    }
}
