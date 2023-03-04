package com.towelove.gateway.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 放行白名单配置
 * 配置来自于nacos注册中心
 * @author: 张锦标
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "security.ignore")
public class IgnoreWhiteProperties
{
    /**
     * 放行白名单配置，网关不校验此处的白名单
     */
    //@Value("${security.ignore.whites}")
    private List<String> whites = new ArrayList<>();

    public List<String> getWhites()
    {
        //whites.add("/auth/login");
        //whites.add("/auth/register");
        //whites.add("/auth/logout");
        //whites.add("/auth/refresh");
        return whites;
    }

    public void setWhites(List<String> whites)
    {
        this.whites = whites;
    }
}
