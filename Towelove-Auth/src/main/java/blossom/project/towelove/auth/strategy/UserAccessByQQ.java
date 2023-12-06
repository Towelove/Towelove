package blossom.project.towelove.auth.strategy;

/**
 * @Author SIK
 * @Date 2023 12 05 15 44
 **/

import blossom.project.towelove.auth.thirdParty.ThirdPartyLoginConfig;
import blossom.project.towelove.client.serivce.RemoteUserService;
import blossom.project.towelove.common.constant.RedisKeyConstant;
import blossom.project.towelove.common.domain.dto.ThirdPartyLoginUser;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.SysUserVo;
import blossom.project.towelove.common.utils.JsonUtils;
import blossom.project.towelove.auth.thirdParty.ThirdPartyLoginUtil;
import blossom.project.towelove.framework.redis.service.RedisService;
import cn.dev33.satoken.stp.StpUtil;
import com.towelove.common.core.constant.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class UserAccessByQQ implements UserAccessStrategy {

    private final RedisService redisService;
    private final RestTemplate restTemplate;
    private final ThirdPartyLoginConfig thirdPartyLoginConfig;
    private final RemoteUserService remoteUserService;

    private final Logger log = LoggerFactory.getLogger(UserAccessByQQ.class);

    final long EXPIRE_TIME = 30; // 过期时间
    final TimeUnit TIME_UNIT = TimeUnit.MINUTES; // 时间单位

    @Override
    public String register(AuthRegisterRequest authRegisterRequest) {
        // 使用授权码获取第三方登录用户信息
        ThirdPartyLoginUser thirdPartyLoginUser = ThirdPartyLoginUtil.getSocialUserInfo(
                thirdPartyLoginConfig,
                restTemplate,
                USER_TYPE.QQ.getType(),
                authRegisterRequest.getVerifyCode(),
                ThirdPartyLoginUser.class
        );

        // 验证第三方用户信息是否获取成功
        if (Objects.isNull(thirdPartyLoginUser) || thirdPartyLoginUser.getSocialUid() == null) {
            throw new ServiceException("无法验证第三方用户信息");
        }

        // 验证成功，将第三方用户信息存储到 Redis 中，以授权码为键
        redisService.setCacheObject(
                RedisKeyConstant.VALIDATE_CODE+authRegisterRequest.getType()+":"+authRegisterRequest.getVerifyCode(),
                JsonUtils.objectToJson(thirdPartyLoginUser),
                EXPIRE_TIME,
                TIME_UNIT);
        return null;
    }

    @Override
    public String login(AuthLoginRequest authLoginRequest) {
        // 使用授权码获取第三方登录用户信息
        ThirdPartyLoginUser thirdPartyLoginUser = ThirdPartyLoginUtil.getSocialUserInfo(
                thirdPartyLoginConfig,
                restTemplate,
                USER_TYPE.QQ.getType(),
                authLoginRequest.getVerifyCode(),
                ThirdPartyLoginUser.class
        );

        // 验证第三方用户信息是否获取成功
        if (Objects.isNull(thirdPartyLoginUser) || thirdPartyLoginUser.getSocialUid() == null) {
            throw new ServiceException("无法验证第三方用户信息");
        }
//        remoteUserService.findUserIdByThirdPartyId(thirdPartyLoginUser);
        // 验证成功，将第三方用户信息存储到 Redis 中，以授权码为键
        Result<Long> sysUserVoResult = remoteUserService.accessByThirdPartyAccount(thirdPartyLoginUser);
        if (Objects.isNull(sysUserVoResult) || HttpStatus.SUCCESS != sysUserVoResult.getCode()){
            throw new ServiceException(sysUserVoResult.getMsg());
        }
        StpUtil.login(sysUserVoResult.getData());
        return StpUtil.getTokenInfo().tokenValue;
    }

    @Override
    public void afterPropertiesSet() {
        log.info("UserAccessByQQ registered successfully");
        UserRegisterStrategyFactory.register(USER_TYPE.QQ.getType(), this);
    }
}

