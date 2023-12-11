package blossom.project.towelove.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.PostConstruct;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author: 张锦标
 * @date: 2023/10/2 15:13
 * SecurityConfig的作用是返回公钥
 */
@Configuration
public class SecurityConfig {
    private KeyPair keyPair;

    @PostConstruct
    public void init() {
        // Generate RSA key pair
        KeyPairGenerator keyGen;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            keyPair = keyGen.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate RSA key pair", e);
        }
    }

    /**
     * 提供给前端获取RSA公钥
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> publicKeyEndpoint() {
        return RouterFunctions.route()
                .GET("/public-key", req -> {
                    String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
                    return ServerResponse.ok().bodyValue(publicKey);
                })
                .build();
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

}