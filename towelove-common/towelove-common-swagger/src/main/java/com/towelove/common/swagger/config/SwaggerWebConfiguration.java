package com.towelove.common.swagger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * swagger 资源映射路径
 * 
 * @author: 张锦标
 * @date: 2023/2/23 20:10
 * Description:
 */
@Configuration
public class SwaggerWebConfiguration implements WebMvcConfigurer
{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        /** swagger-ui 地址 */
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
    }
}