package blossom.project.towelove.gateway.filter.satoken;

import blossom.project.towelove.common.constant.SecurityConstant;
import blossom.project.towelove.common.constant.TokenConstant;
import blossom.project.towelove.common.constant.UserConstants;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.response.user.LoginUserResponse;
import blossom.project.towelove.framework.redis.service.RedisService;
import blossom.project.towelove.gateway.config.UserContextHolder;
import cn.hutool.core.util.URLUtil;
import cn.hutool.system.UserInfo;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReWriteRequestFilter implements GlobalFilter , Ordered {

    private final Logger log = LoggerFactory.getLogger(ReWriteRequestFilter.class);

    private final RedisTemplate<String,String> redisTemplate;

    /**
     * 重写request 携带userId分发到后续请求
     * @param exchange the current server exchange
     * @param chain provides a way to delegate to the next filter
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //判断白名单
        if (judgeWhite(request)) {
            return chain.filter(exchange.mutate().request(request).build());
        }
        String token = request.getHeaders().get(TokenConstant.AUTHENTICATION).get(0);
        String loginIdAsString = null;
        try {
            loginIdAsString = redisTemplate.opsForValue().get(TokenConstant.AUTHENTICATION_ON_REDIS + token);
        } catch (Exception e) {
            log.error("获取用户信息失败,失败原因为：{}",e.getMessage());
            e.printStackTrace();
            throw new ServiceException("从SaToken获取用户信息失败");
        }
        LoginUserResponse sysUser = JSON.parseObject(loginIdAsString, LoginUserResponse.class);
//        重写请求
        reBuildRequest(sysUser,request);
//        judgeRefreshToken();
        return chain.filter(exchange.mutate().request(request).build());
    }

//    private void judgeRefreshToken() {
//        long tokenActiveTimeout = StpUtil.getTokenActiveTimeout();
//        if (tokenActiveTimeout < 60 * 60L){
//            StpUtil.updateLastActiveToNow();//刷新token有效期
//        }
//    }

    public boolean judgeWhite(ServerHttpRequest request){
        URI uri = request.getURI();
        return uri.getPath().contains("/v1/auth");
    }


    public Mono<Void> dealException(ServerWebExchange exchange,Exception e){
        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(e.getMessage().getBytes());
        exchange.getResponse().setStatusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    public void reBuildRequest(LoginUserResponse sysUser,ServerHttpRequest request){
        String userName = sysUser.getUserName();
        String nickName = sysUser.getNickName();
        String sex = sysUser.getSex();
        String token = request.getHeaders().get(TokenConstant.AUTHENTICATION).get(0);
        List<String> requestId = request.getHeaders().get(SecurityConstant.REQUEST_ID);
        String requestIdStr = "";
        if (Objects.nonNull(requestId) && !requestId.isEmpty()){
            requestIdStr = Optional.ofNullable(requestId.get(0)).orElseGet(() -> "test");
        }
        request.mutate()
                .header(TokenConstant.USER_ID_HEADER, String.valueOf(sysUser.getId()))
                .header(TokenConstant.USER_NAME_HEADER, URLEncoder.encode(userName, StandardCharsets.UTF_8))
                .header(TokenConstant.USER_NICK_HEADER,URLEncoder.encode(nickName,StandardCharsets.UTF_8))
                .header(TokenConstant.USER_SEX,URLEncoder.encode(sex,StandardCharsets.UTF_8))
                .header(TokenConstant.USER_TOKEN,token)
                .header(TokenConstant.USER_EMAIL,sysUser.getEmail())
                .header(TokenConstant.USER_PHONE,sysUser.getEmail())
                .header(SecurityConstant.REQUEST_ID,requestIdStr)
                .build();
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
