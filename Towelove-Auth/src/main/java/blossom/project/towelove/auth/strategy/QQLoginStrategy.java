package blossom.project.towelove.auth.strategy;

/**
 * @Author SIK
 * @Date 2023 12 05 15 44
 **/

import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QQLoginStrategy implements UserRegisterStrategy {

    // 注入服务，RestTemplate，配置类等

    @Autowired
    public QQLoginStrategy() {
        // 构造器注入所需组件
    }

    @Override
    public boolean valid(AuthLoginRequest authLoginRequest) {
        // 实现QQ登录验证逻辑
        // 这里可以调用QQ登录验证的接口等
        return false;
    }

    @Override
    public void afterPropertiesSet() {
        UserRegisterStrategyFactory.register(USER_TYPE.QQ.getType(), this);
    }
}
