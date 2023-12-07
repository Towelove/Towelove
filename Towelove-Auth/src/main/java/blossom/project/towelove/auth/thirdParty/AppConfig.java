package blossom.project.towelove.auth.thirdParty;

/**
 * @Author SIK
 * @Date 2023 12 05 10 29
 **/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
