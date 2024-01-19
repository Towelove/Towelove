package blossom.project.towelove.user.interceptor;

import blossom.project.towelove.framework.redis.service.RedisService;
import blossom.project.towelove.user.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.config
 * @className: WebConfiguration
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 14:55
 * @version: 1.0
 */
@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
    private final RedisService redisService;

    private final SysUserMapper sysUserMapper;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInfoInterceptor(redisService,sysUserMapper));
    }
}
