package blossom.project.towelove.gateway.route;

import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.framework.redis.service.RedisService;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;

@Configuration
public class RouterFunctionConfig {
    @Resource
    private RedisService redisService;

    private static final int RADIX_10 = 10;
    private static final int HASH_MODULUS = 10;
    private static final int RADIX_36 = 36;

    public final static String URL_MAPPING_FROM = "url_mapping:%s";

    @Bean
    public RouterFunction routerFunction1()
    {
        //传入的请求通过RouterFunction直接返回数据
        return RouterFunctions.route(
                RequestPredicates.path("/code1")
        ,request -> ServerResponse.ok().body(
                        BodyInserters.fromValue("hello")
                ));
    }

    /**
     * 短链解析请求
     */
    @Bean
    public RouterFunction praseShortUrlRouterFunction(){
        return RouterFunctions.route(
                RequestPredicates.path("/surl/**"),
                request -> {
                    String path = request.path();
                    String shortUrl = path.substring(path.lastIndexOf("/") + 1);
                    if (StrUtil.isBlank(shortUrl)){
                        throw new ServiceException("短链无效");
                    }
                    String sourceUrl = parseShortUrl(shortUrl);
                    return ServerResponse.status(HttpStatus.FOUND)
                            .header("Location",sourceUrl)
                            .body(BodyInserters.fromValue("hello"));
                }
        );
    }
    public String parseShortUrl(String shortUrl){
        String hash = new BigInteger(shortUrl, RADIX_36).toString(RADIX_10);
        int kIndex = Integer.parseInt(hash) % HASH_MODULUS;
        Object cacheMapValue = redisService.getCacheMapValue(getUrlMappingKey(kIndex), shortUrl);
        if (Objects.isNull(cacheMapValue)) {
            throw new ServiceException("短链无效");
        }
        return String.valueOf(cacheMapValue);
    }

    private String getUrlMappingKey(int kIndex){
//        return String.format(ShortUrlCacheConstants.URL_MAPPING_FROM,from);
        return String.format(URL_MAPPING_FROM,kIndex);
    }
}
