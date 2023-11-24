package blossom.project.towelove.common.entity.msg;

import lombok.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@AutoConfiguration
@ConfigurationProperties(prefix = "spring.mail")
public class OfficialMailInfo {
    private Integer port;
    private String host;
    private String username;
    private String password;
    private String protocol;
    private Boolean sslEnable;
}
