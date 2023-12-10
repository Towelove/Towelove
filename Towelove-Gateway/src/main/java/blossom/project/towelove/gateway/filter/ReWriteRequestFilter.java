package blossom.project.towelove.gateway.filter;

import blossom.project.towelove.common.constant.TokenConstant;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ReWriteRequestFilter implements GlobalFilter , Ordered {
    /**
     * 重写request 携带userId分发到后续请求
     * @param exchange the current server exchange
     * @param chain provides a way to delegate to the next filter
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String id = "";
        try{
             id = StpUtil.getLoginId().toString();
        }catch (Exception e){
            //如果请求没有loginId就报错并且直接返回原因
            //设定返回内容和返回状态码
            DataBuffer dataBuffer =exchange.getResponse().bufferFactory().wrap(e.getMessage().getBytes());
            exchange.getResponse().setStatusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        }
        //重写请求
        ServerHttpRequest requst = exchange
                .getRequest()
                .mutate()
                .header(TokenConstant.USER_ID_HEADER, id).build();
        return chain.filter(exchange.mutate().request(requst).build());
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
