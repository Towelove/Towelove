package com.towelove.gateway.filter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.towelove.common.core.constant.RedisServiceConstants;
import com.towelove.common.redis.service.RedisService;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
 */
@Component
public class IPCheckFilter implements GlobalFilter, Ordered {

    /**
     * 网关的并发比较高 不要再网关里面直接操作mysql
     * 后台系统可以查询数据库 用户量 并发量不大
     * 如果并发量大 可以查redis 或者 在内存中写好
     */

    @Resource
    private RedisService redisService;
    //初始化黑名单
    public static List<String> BLACK_LIST ;
    @PostConstruct
    public void initBlackList()
    {
        BLACK_LIST = redisService.getCacheList(RedisServiceConstants.BLACK_LIST_IP);
    }
    /**
     * 1.拿到ip
     * 2.校验ip是否符合规范
     * 3.放行/拦截
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String ip = request.getHeaders().getHost().getHostString();
        System.out.println("-------当前访问ip为："+ip+"---------");
        // 查询数据库 看这个ip是否存在黑名单里面   mysql数据库的并发
        // 只要是能存储数据地方都叫数据库 redis  mysql
        if (!BLACK_LIST.contains(ip)) {
            return chain.filter(exchange);
        }
        // 拦截
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("content-type","application/json;charset=utf-8");
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("code", 438);
        map.put("msg","你是黑名单");
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

    @Override
    public int getOrder() {
        return -300;
    }
}
