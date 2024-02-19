package blossom.project.towelove.gateway.filter.crypto;

import blossom.project.towelove.gateway.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * @author 张锦标
 * 对称密钥保存过滤器
 * 当前过滤器首先会先获取请求头中的对称密钥
 * 如果有，那么获取对称密钥并且保存到Redis中
 */
//@Component
public class SymmetricKeyFilter implements GlobalFilter, Ordered {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    //TODO 3：这里会把加密好的对称密钥 解密 然后放入到redis中
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String encryptedSymmetricKey = exchange.getRequest().getHeaders().getFirst("X-Encrypted-Symmetric-Key");
        if (encryptedSymmetricKey != null) {
            try {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, securityConfig.getKeyPair().getPrivate());
                byte[] decryptedKeyBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedSymmetricKey));
                //得到对称密钥
                String symmetricKey = new String(decryptedKeyBytes, StandardCharsets.UTF_8);

                ////在非阻塞上下文中阻塞调用可能会导致线程饥饿
                ////TODO 需要优化一下这里 来确保每个请求可以唯一对应一个加密密钥
                //String sessionId = exchange.getSession().block().getId();
                //stringRedisTemplate.opsForValue().set(sessionId, symmetricKey);
                String redisSymmetricKey = "symmetric:key:"+1;
                stringRedisTemplate.opsForValue().set(redisSymmetricKey, symmetricKey);

            } catch (Exception e) {
                e.printStackTrace();
                String responseBody = "there are something wrong occurs when decrypt your key!!!";
                //GatewayUtil.responseMessage(exchange,responseBody);

                // 获取响应对象
                ServerHttpResponse response = exchange.getResponse();
                ////处理对称密钥出现了问题
                response.setRawStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
                //
                //// 返回你想要的字符串
                return response.writeWith(
                        Mono.just(response.bufferFactory().wrap(responseBody.getBytes())));
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -300;
    }

}