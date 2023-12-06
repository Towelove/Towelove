package blossom.project.towelove.auth.strategy;

/**
 * @Author SIK
 * @Date 2023 12 05 15 44
 **/

import blossom.project.towelove.common.config.thirdParty.ThirdPartyLoginConfig;
import blossom.project.towelove.common.constant.RedisKeyConstant;
import blossom.project.towelove.common.domain.dto.ThirdPartyLoginUser;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.utils.JsonUtils;
import blossom.project.towelove.common.utils.ThirdPartyLoginUtil;
import blossom.project.towelove.framework.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class QQRegisterStrategy implements UserRegisterStrategy {

    private final RedisService redisService;
    private final RestTemplate restTemplate;
    private final ThirdPartyLoginConfig thirdPartyLoginConfig;

    final long EXPIRE_TIME = 30; // 过期时间
    final TimeUnit TIME_UNIT = TimeUnit.MINUTES; // 时间单位

    @Override
    public boolean valid(AuthLoginRequest authLoginRequest) {
        // 使用授权码获取第三方登录用户信息
        ThirdPartyLoginUser thirdPartyLoginUser = ThirdPartyLoginUtil.getSocialUserInfo(
                thirdPartyLoginConfig,
                restTemplate,
                USER_TYPE.QQ.getType(),
                authLoginRequest.getVerifyCode(),
                ThirdPartyLoginUser.class
        );

        // 验证第三方用户信息是否获取成功
        if (thirdPartyLoginUser == null || thirdPartyLoginUser.getSocialUid() == null) {
            throw new ServiceException("无法验证第三方用户信息");
        }

        // 验证成功，将第三方用户信息存储到 Redis 中，以授权码为键
        redisService.redisTemplate.opsForValue().set(
                RedisKeyConstant.VALIDATE_CODE+authLoginRequest.getType()+":"+authLoginRequest.getVerifyCode(),
                JsonUtils.objectToJson(thirdPartyLoginUser),
                EXPIRE_TIME,
                TIME_UNIT);
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("QQRegisterStrategy registered successfully");
        UserRegisterStrategyFactory.register(USER_TYPE.QQ.getType(), this);
    }
}

