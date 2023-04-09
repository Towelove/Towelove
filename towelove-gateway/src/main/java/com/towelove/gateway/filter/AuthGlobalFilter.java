package com.towelove.gateway.filter;

import com.towelove.common.core.constant.*;
import com.towelove.common.core.utils.JwtUtils;
import com.towelove.common.core.utils.ServletUtils;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.common.redis.service.RedisService;
import com.towelove.gateway.config.properties.IgnoreWhiteProperties;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * 网关鉴权
 *
 * @author: 张锦标
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(AuthGlobalFilter.class);

    // 排除过滤的 uri 地址，nacos自行添加
    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    @Autowired
    private RedisService redisService;
    //单个IP/s访问上限
    public static final int UP_LIMIT = 10;
    public static Set<String> BLACK_LIST ;

    @PostConstruct
    public void initIpList(){
        BLACK_LIST = redisService.getCacheSet(
                RedisServiceConstants.BLACK_LIST_IP);
    }

    //来自auth的请求会先通过当前接口
    //并且来自/auth的请求直接进行放行
    //当前过滤器要求再用户登录成功之后
    //其访问其他路径的请求的时候需要进行拦截
    //判断其请求头中是否有对应的用户信息
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //获得当前ip
        String ip = request.getHeaders().getHost().getHostString();
        //获得当前ip访问次数
        Integer times = (Integer) redisService.getCacheObject(
                RedisServiceConstants.USING_SYS_IP+ip);
        if (Objects.isNull(times)) {
            redisService.setCacheObject(RedisServiceConstants.USING_SYS_IP + ip,
                    1,3L, TimeUnit.SECONDS);
        } else {
            //如果达到上限
            if (times+1==UP_LIMIT){
                BLACK_LIST.add(ip);
                redisService.setCacheSet(RedisServiceConstants.BLACK_LIST_IP,
                        BLACK_LIST);
                return chain.filter(exchange);
            }
            redisService.setCacheObject(RedisServiceConstants.USING_SYS_IP + ip,
                    times+1, 3L,TimeUnit.SECONDS);
        }
        ServerHttpRequest.Builder mutate = request.mutate();

        String url = request.getURI().getPath();
        //跳过不需要验证的路径 路径来自于nacos 比如登录和注册
        if (StringUtils.matches(url, ignoreWhite.getWhites())) {
            return chain.filter(exchange);
        }
        //非登录和注册请求需要进行token获取
        String token = getToken(request);
        if (StringUtils.isEmpty(token)) {
            return unauthorizedResponse(exchange, "令牌不能为空");
        }
        Claims claims = JwtUtils.parseToken(token);
        if (claims == null) {
            return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
        }
        String userkey = JwtUtils.getUserKey(claims);
        boolean islogin = redisService.hasKey(getTokenKey(userkey));

        if (!islogin) {
            return unauthorizedResponse(exchange, "登录状态已过期");
        }
        String userid = JwtUtils.getUserId(claims);
        String username = JwtUtils.getUserName(claims);
        if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username)) {
            return unauthorizedResponse(exchange, "令牌验证失败");
        }

        // 设置用户信息到请求头
        //USER_KEY对应token
        //USER_ID能方便之后进行用户查询
        addHeader(mutate, SecurityConstants.USER_KEY, userkey);
        addHeader(mutate, SecurityConstants.DETAILS_USER_ID, userid);
        addHeader(mutate, SecurityConstants.DETAILS_USERNAME, username);
        // 内部请求来源参数清除
        removeHeader(mutate, SecurityConstants.FROM_SOURCE);
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
        if (value == null) {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = ServletUtils.urlEncode(valueStr);
        mutate.header(name, valueEncode);
    }

    private void removeHeader(ServerHttpRequest.Builder mutate, String name) {
        mutate.headers(httpHeaders -> httpHeaders.remove(name)).build();
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg) {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), msg, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 获取缓存key
     */
    private String getTokenKey(String token) {
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }

    /**
     * 获取请求token
     */
    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(TokenConstants.AUTHENTICATION);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
            token = token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
        }
        return token;
    }

    @Override
    public int getOrder() {
        return -200;
    }
}