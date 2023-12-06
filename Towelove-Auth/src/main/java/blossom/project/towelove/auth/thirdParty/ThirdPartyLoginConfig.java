package blossom.project.towelove.auth.thirdParty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author SIK
 * @Date 2023 12 02 18 01
 **/
@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyLoginConfig {
    @Value("${third.config.appId}")
    private Integer appId;

    @Value("${third.config.appKey}")
    private String appKey;

    @Value("${third.config.redirectUrl}")
    private String redirectUrl;


    public ThirdPartyLoginConfig thirdPartyLoginConfig() {
        return new ThirdPartyLoginConfig(appId, appKey, redirectUrl);
    }
}
