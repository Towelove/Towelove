package com.towelove.system.domain.mail;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2023/3/7 18:05
 * MailConfiguration类
 */
@Data
@ToString
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailConfiguration {
    private String port;
    private String host;
    private String username;
    private String password;
    private String protocol;


}
