package blossom.project.towelove.auth.service.impl;

import blossom.project.towelove.auth.service.AuthService;
import blossom.project.towelove.client.serivce.RemoteUserService;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import blossom.project.towelove.common.request.auth.AuthVerifyCodeRequest;
import blossom.project.towelove.common.response.Result;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.towelove.common.core.constant.HttpStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final RemoteUserService remoteUserService;


    @Override
    public String register(@Valid AuthRegisterRequest authLoginRequest) {
        //校验手机号以及邮箱格式，校验验证码格式是否正确
        check(authLoginRequest);
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(authLoginRequest,sysUser);
        Result<String> result = remoteUserService.saveUser(sysUser);
        log.info("调用user远程服务获取到的接口为: {}",result);
        if (Objects.isNull(result) || result.getCode() != HttpStatus.SUCCESS){
            throw new ServiceException(result.getMsg());
        }
        StpUtil.login(result.getData());
        return StpUtil.getTokenInfo().tokenValue;
    }

    @Override
    public String login(AuthLoginRequest authLoginRequest) {
        check(authLoginRequest);
        //验证码校验通过
        //查询用户是否存在
        Result<String> result = remoteUserService.findUserByPhoneOrEmail(authLoginRequest);
        log.info("调用user远程服务获取到的接口为: {}",result);
        if (Objects.isNull(result) || result.getCode() != (HttpStatus.SUCCESS)){
            //用户不存在
            throw new ServiceException("用户不存在，请重新注册");
        }
        StpUtil.login(result.getData());
        return StpUtil.getTokenInfo().tokenValue;
    }

    @Override
    public String sendVerifyCode(AuthVerifyCodeRequest authVerifyCodeRequest) {
        if (StrUtil.isNotBlank(authVerifyCodeRequest.getPhone())){
            checkPhoneByRegex(authVerifyCodeRequest.getPhone());
            //TODO 调用远程验证码接口
        }
        if (StrUtil.isNotBlank(authVerifyCodeRequest.getEmail())){
            checkEmailByRegex(authVerifyCodeRequest.getEmail());
            //TODO 调用远程验证码接口
        }
        return null;
    }

    public void check (AuthLoginRequest authLoginRequest){
        //校验验证码是否正确
        //TODO：等待验证码
        String phone = authLoginRequest.getPhoneNumber();
        String email = authLoginRequest.getEmail();
        String code = authLoginRequest.getVerifyCode();
        if (StrUtil.isNotBlank(phone)){
            checkPhoneByRegex(phone);
            checkVerifyCode(phone,code);
            return;
        }
        if (StrUtil.isNotBlank(email)){
            checkEmailByRegex(email);
            checkVerifyCode(email,code);
            return;
        }
        throw new ServiceException("验证码校验失败,手机号与验证码为空");
    }
    public boolean checkVerifyCode(String key,String code){
        //TODO 等待验证码接口
        return true;
    }
    public void checkPhoneByRegex(String phone){
        if (!ReUtil.isMatch(RegexPool.MOBILE,phone)) {
            throw new ServiceException("手机号格式错误");
        }
    }
    public void checkEmailByRegex(String email){
        if (!ReUtil.isMatch(RegexPool.EMAIL,email)) {
            throw new ServiceException("邮箱格式错误");
        }
    }
}
