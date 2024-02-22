package blossom.project.towelove.gateway.filter.satoken;

import blossom.project.towelove.common.constant.TokenConstant;
import blossom.project.towelove.common.constant.UserConstants;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.framework.redis.service.RedisService;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.system.UserInfo;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class ReWriteRequestFilter implements GlobalFilter , Ordered {

    private final RedisService redisService;
    /**
     * 重写request 携带userId分发到后续请求
     * @param exchange the current server exchange
     * @param chain provides a way to delegate to the next filter
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        //判断白名单
        if (!uri.getPath().contains("/v1/auth")) {
            SysUser sysUser = null;
            try{
                sysUser = JSON.parseObject(StpUtil.getLoginIdAsString(), SysUser.class);
            }catch (Exception e){
                //如果请求没有loginId就报错并且直接返回原因
                //设定返回内容和返回状态码
                DataBuffer dataBuffer =exchange.getResponse().bufferFactory().wrap(e.getMessage().getBytes());
                exchange.getResponse().setStatusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
                return exchange.getResponse().writeWith(Mono.just(dataBuffer));
            }
            //从Redis中获取用户信息
//        redisService.getCacheObject(TokenConstant.)
            //重写请求
            ServerHttpRequest requst = request
                    .mutate()
                    .header(TokenConstant.USER_ID_HEADER, String.valueOf(sysUser.getId()))
                    .header(TokenConstant.USER_NAME_HEADER,sysUser.getUserName())
                    .header(TokenConstant.USER_NICK_HEADER,sysUser.getNickName())
                    .header(TokenConstant.USER_SEX,sysUser.getSex())
                    .header(TokenConstant.USER_TOKEN,StpUtil.getTokenValue())
                    .build();
        }
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
