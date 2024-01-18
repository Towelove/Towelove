package blossom.project.towelove.auth.strategy;

import blossom.project.towelove.client.serivce.RemoteUserService;
import blossom.project.towelove.common.constant.RedisKeyConstant;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.redis.service.RedisService;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.towelove.common.core.constant.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.strategy
 * @className: UserAccessByPhone
 * @author: Link Ji
 * @description:
 * @date: 2023/11/24 21:18
 * @version: 1.0
 */
@Component
@RequiredArgsConstructor
public class UserAccessByEmail implements UserAccessStrategy {

    private final Logger log = LoggerFactory.getLogger(UserAccessByEmail.class);
    private final RedisService redisService;

    private final RemoteUserService remoteUserService;

    @Override
    public SysUser register(AuthRegisterRequest authRegisterRequest) {
        String email = authRegisterRequest.getEmail();
        String code = authRegisterRequest.getVerifyCode();
        log.info("校验验证码请求的邮箱号为：{},验证码为：{}",email,code);
        if (StrUtil.isNotBlank(email) && ReUtil.isMatch(RegexPool.EMAIL,email)){
            String codeFromSystem = (String) redisService.redisTemplate.opsForValue().get(RedisKeyConstant.VALIDATE_CODE + email);
            if (StrUtil.isBlank(codeFromSystem)){
                throw new ServiceException("验证码校验失败，未发送验证码");
            }
            if (!code.equals(codeFromSystem)){
                throw new ServiceException("验证码校验失败，验证码错误");
            }
            SysUser sysUser = new SysUser();
            BeanUtils.copyProperties(authRegisterRequest,sysUser);
           return sysUser;
        }
        return null;
    }

    @Override
    public SysUser login(AuthLoginRequest authLoginRequest) {
        String email = authLoginRequest.getEmail();
        String code = authLoginRequest.getVerifyCode();
        log.info("校验验证码请求的邮箱号为：{},验证码为：{}",email,code);
        if (StrUtil.isNotBlank(email) && ReUtil.isMatch(RegexPool.EMAIL,email)){
            String codeFromSystem = (String) redisService.redisTemplate.opsForValue().get(RedisKeyConstant.VALIDATE_CODE + email);
            if (StrUtil.isBlank(codeFromSystem)){
                throw new ServiceException("验证码校验失败，未发送验证码");
            }
            if (!code.equals(codeFromSystem)){
                throw new ServiceException("验证码校验失败，验证码错误");
            }
            Result<SysUser> result = remoteUserService.findUserByPhoneOrEmail(authLoginRequest);
            if (Objects.isNull(result) || HttpStatus.SUCCESS != result.getCode()){
                throw new ServiceException("用户登入失败，请联系管理员");
            }
            return result.getData();
        }
        return null;
    }


    @Override
    public void afterPropertiesSet(){
        UserRegisterStrategyFactory.register(USER_TYPE.EMAIL.type, this);
    }
}
