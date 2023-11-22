package blossom.project.towelove.msg.entity;

import lombok.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class OfficialMailInfo {
    private Integer port;
    private String host;
    private String username;
    private String password;
    private String protocol;


}
