package blossom.project.towelove.auth.strategy;

import blossom.project.towelove.client.serivce.msg.RemoteValidateService;
import blossom.project.towelove.client.serivce.user.RemoteUserService;
import blossom.project.towelove.common.constant.RedisKeyConstant;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import blossom.project.towelove.common.request.msg.ValidateCodeRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.LoginUserResponse;
import blossom.project.towelove.framework.redis.service.RedisService;
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

    private final RemoteValidateService remoteValidateService;

    @Override
    public SysUser register(AuthRegisterRequest authRegisterRequest) {
        String email = authRegisterRequest.getEmail();
        String code = authRegisterRequest.getVerifyCode();
        log.info("校验验证码请求的邮箱号为：{},验证码为：{}",email,code);
        if (StrUtil.isNotBlank(email) && ReUtil.isMatch(RegexPool.EMAIL,email)){
            ValidateCodeRequest validateCodeRequest = ValidateCodeRequest.builder()
                    .number(email)
                    .code(code)
                    .type(authRegisterRequest.getType())
                    .build();
            Result<String> validate = remoteValidateService.validate(validateCodeRequest);
            if (Objects.isNull(validate) || validate.getCode() != HttpStatus.SUCCESS){
                throw new ServiceException(validate.getMsg());
            }
            SysUser sysUser = new SysUser();
            BeanUtils.copyProperties(authRegisterRequest,sysUser);
           return sysUser;
        }
        return null;
    }

    @Override
    public LoginUserResponse login(AuthLoginRequest authLoginRequest) {
        String email = authLoginRequest.getEmail();
        String code = authLoginRequest.getVerifyCode();
        log.info("校验验证码请求的邮箱号为：{},验证码为：{}",email,code);
        if (StrUtil.isBlank(email) || !ReUtil.isMatch(RegexPool.EMAIL,email)){
           throw new ServiceException("请求非法，邮件为空或格式错误");
        }
        ValidateCodeRequest validateCodeRequest = ValidateCodeRequest.builder()
                .number(email)
                .code(code)
                .type(authLoginRequest.getType())
                .build();
        Result<String> validate = remoteValidateService.validate(validateCodeRequest);
        if (Objects.isNull(validate) || validate.getCode() != HttpStatus.SUCCESS){
            throw new ServiceException(validate.getMsg());
        }
        Result<LoginUserResponse> result = remoteUserService.findUserByPhoneOrEmail(authLoginRequest);
        if (Objects.isNull(result) || HttpStatus.SUCCESS != result.getCode()){
            throw new ServiceException("用户登入失败，请联系管理员");
        }
        redisService.deleteObject(RedisKeyConstant.VALIDATE_CODE + email);
        return result.getData();

    }


    @Override
    public void afterPropertiesSet(){
        UserRegisterStrategyFactory.register(USER_TYPE.EMAIL.type, this);
    }
}
