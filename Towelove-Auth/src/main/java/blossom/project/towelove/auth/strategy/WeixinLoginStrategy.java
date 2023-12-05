package blossom.project.towelove.auth.strategy;

/**
 * @Author SIK
 * @Date 2023 12 05 15 52
 **/

import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeixinLoginStrategy implements UserRegisterStrategy {

    // 注入服务，RestTemplate，配置类等

    @Autowired
    public WeixinLoginStrategy() {
        // 构造器注入所需组件
    }

    @Override
    public boolean valid(AuthLoginRequest authLoginRequest) {
        // 实现Weixin登录验证逻辑
        // 这里可以调用Weixin登录验证的接口等
        return false;
    }

    @Override
    public void afterPropertiesSet() {
        UserRegisterStrategyFactory.register(USER_TYPE.WEIXIN.getType(), this);
    }
}

