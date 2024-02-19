package blossom.project.towelove.gateway.filter.crypto;

import blossom.project.towelove.common.utils.CryptoHelper;
import blossom.project.towelove.common.utils.GatewayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author 张锦标
 * 当前类首先会解析加密后的URL
 * 当前类用于解析参数 如果参数解密后和signature不一样则返回
 * 并且会重新设定路由路径
 */
//@Component
public class CryptoFilter implements GlobalFilter, Ordered {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CryptoHelper cryptoHelper;


    //TODO 4：在这里对加密的URL进行解密
    //并且会得到路径的参数
    //然后对参数进行加密之后和signature比较判断是否被修改
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //String sessionId = exchange.getSession().block().getId();
        String redisSymmetricKey = "symmetric:key:" + 1;
        //String symmetricKey = stringRedisTemplate.opsForValue().get(sessionId);
        String symmetricKey = stringRedisTemplate.opsForValue().get(redisSymmetricKey);
        if (symmetricKey == null) {
            return GatewayUtil.responseMessage(exchange, "this session has not symmetricKey!!!");
            //String responseBody = "this session has not symmetricKey!!!";
            //// 获取响应对象
            //ServerHttpResponse response = exchange.getResponse();
            //////处理对称密钥出现了问题
            //response.setRawStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            //response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
            ////
            ////// 返回你想要的字符串
            //return response.writeWith(
            //        Mono.just(response.bufferFactory().wrap(responseBody.getBytes())));

        }

        try {
            //URL动态加密  数字签名 signature
            //如果URL已加密，则解密该URL
            //path:/v1/product/encrypt/WyYSV30Cor8QX/eWGsQ7yPD3EvNRRS0HF845UOb+KAdwHPKZByMa3250J/z2S4at
            //uri:http://localhost:8080/v1/product/encrypt/WyYSV30Cor8QX/eWGsQ7yPD3EvNRRS0HF845UOb+KAdwHPKZByMa3250J/z2S4at
            String encryptedUrl = exchange.getRequest().getURI().toString();
            String path = exchange.getRequest().getURI().getPath();
            String encryptPathParam = path.substring(path.indexOf("/encrypt/") + 9);
            String decryptedPathParam = cryptoHelper.decryptUrl(encryptPathParam, symmetricKey);
            String decryptedUri =
                    encryptedUrl.substring(0, encryptedUrl.indexOf("/encrypt/"))
                            .concat("?").concat(decryptedPathParam);
            //这个方法直接修改的是exchange里面的request
            exchange = exchange.mutate().request(build -> {
                try {
                    build.uri(new URI(decryptedUri));
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }).build();
            //TODO 需要前端这里首先按照前后端约定的加密方式进行一次加密
            //然后得到一个signature，放在请求的末尾
            //然后对整个URL进行加密请求
            // 解析解密后的URL以获取解密的查询参数
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(decryptedUri).build();
            MultiValueMap<String, String> decryptedQueryParams = uriComponents.getQueryParams();

            // 验证请求参数的签名
            String signature = decryptedQueryParams.getFirst("signature");
            if (!cryptoHelper.verifySignature(decryptedQueryParams, signature, symmetricKey)) {

                return GatewayUtil.responseMessage(exchange,
                        "the param has something wrong!!!");
            }

        } catch (Exception e) {
            return GatewayUtil.responseMessage(exchange,
                    "the internal server occurs an error!!!");
        }

        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return -200;
    }
}