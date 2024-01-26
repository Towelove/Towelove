package blossom.project.towelove.auth.strategy;

import blossom.project.towelove.client.serivce.user.RemoteUserService;
import blossom.project.towelove.common.constant.RedisKeyConstant;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.redis.service.RedisService;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.towelove.common.core.constant.HttpStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.strategy
 * @className: UserAccessByPhone
 * @author: Link Ji
 * @description: TODO
 * @date: 2023/11/24 21:18
 * @version: 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UserAccessByPhone implements UserAccessStrategy {
    private final RedisService redisService;

    private final RemoteUserService remoteUserService;
    @Override
    public SysUser register(AuthRegisterRequest authRegisterRequest) {
        String phone = authRegisterRequest.getPhoneNumber();
        String code = authRegisterRequest.getVerifyCode();
        log.info("校验验证码请求的手机号为：{},验证码为：{}",phone,code);
        if (StrUtil.isNotBlank(phone) && ReUtil.isMatch(RegexPool.MOBILE,phone)){
            //手机号校验通过，校验验证码
            String codeFromSystem = (String) redisService.redisTemplate.opsForValue().get(RedisKeyConstant.VALIDATE_CODE + phone);
            if (StrUtil.isBlank(codeFromSystem)){
                throw new ServiceException("验证码校验失败，未发送验证码");
            }
            if (!code.equals(codeFromSystem)){
                throw new ServiceException("验证码校验失败，验证码错误");
            }
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(authRegisterRequest,sysUser);
        return sysUser;
    }

    @Override
    public SysUser login(AuthLoginRequest authLoginRequest) {
        String phone = authLoginRequest.getPhoneNumber();
        String code = authLoginRequest.getVerifyCode();
        log.info("校验验证码请求的手机号为：{},验证码为：{}",phone,code);
        if (StrUtil.isNotBlank(phone) && ReUtil.isMatch(RegexPool.MOBILE,phone)){
            //手机号校验通过，校验验证码
            String codeFromSystem = (String) redisService.redisTemplate.opsForValue().get(RedisKeyConstant.VALIDATE_CODE + phone);
            if (StrUtil.isBlank(codeFromSystem)){
                throw new ServiceException("验证码校验失败，未发送验证码");
            }
            if (!code.equals(codeFromSystem)){
                throw new ServiceException("验证码校验失败，验证码错误");
            }
        }
        Result<SysUser> userByPhoneOrEmail = remoteUserService.findUserByPhoneOrEmail(authLoginRequest);
        if (Objects.isNull(userByPhoneOrEmail) || HttpStatus.SUCCESS != userByPhoneOrEmail.getCode()){
            throw new ServiceException("用户不存在，请注册");
        }
        return userByPhoneOrEmail.getData();

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UserRegisterStrategyFactory.register(USER_TYPE.PHONE.type, this);
    }
}
