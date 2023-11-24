package blossom.project.towelove.auth.strategy;

import blossom.project.towelove.common.constant.RedisKeyConstant;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import blossom.project.towelove.framework.redis.service.RedisService;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.strategy
 * @className: RegisterByPhone
 * @author: Link Ji
 * @description: TODO
 * @date: 2023/11/24 21:18
 * @version: 1.0
 */
@Component
@Slf4j
public class RegisterByPhone implements UserRegisterStrategy{
    private final RedisService redisService;
    public RegisterByPhone(RedisService redisService){
        this.redisService  = redisService;
    }
    @Override
    public boolean valid(AuthLoginRequest authLoginRequest) {
        String phone = authLoginRequest.getPhoneNumber();
        String code = authLoginRequest.getVerifyCode();
        log.info("校验验证码请求的手机号为：{},验证码为：{}",phone,code);
        if (StrUtil.isNotBlank(phone) && ReUtil.isMatch(RegexPool.MOBILE,phone)){
            //手机号校验通过，校验验证码
            //TODO 等待验证码接口
            String codeFromSystem = (String) redisService.redisTemplate.opsForValue().get(RedisKeyConstant.VALIDATE_CODE + phone);
            if (StrUtil.isBlank(codeFromSystem)){
                throw new ServiceException("验证码校验失败，未发送验证码");
            }
            if (!code.equals(codeFromSystem)){
                throw new ServiceException("验证码校验失败，验证码错误");
            }
            return true;
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UserRegisterStrategyFactory.register(USER_TYPE.PHONE.type, this);
    }
}
