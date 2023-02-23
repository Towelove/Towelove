package com.towelove.user.api.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2023/2/23 18:51
 * Description:
 */
@Component
public class UserAuthFallbackFactory implements FallbackFactory<UserAuthService>{
    private static final Logger log = LoggerFactory.getLogger(UserAuthFallbackFactory.class);

    @Override
    public UserAuthService create(Throwable throwable)
    {
        log.error("用户登录调用失败:{}", throwable.getMessage());
        return new UserAuthService()
        {
        };
    }
}
