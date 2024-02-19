package blossom.project.towelove.common.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/10 15:19
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * GatewayUtil类
 */
public class GatewayUtil {
    public static Mono<Void> responseMessage(ServerWebExchange exchange, String responseBody) {
         //获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        //处理对称密钥出现了问题
        response.setRawStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);

         //返回你想要的字符串
        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(responseBody.getBytes())));
    }
}
