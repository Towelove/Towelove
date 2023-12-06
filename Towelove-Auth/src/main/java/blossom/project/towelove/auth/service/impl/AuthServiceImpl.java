package blossom.project.towelove.auth.service.impl;

import blossom.project.towelove.auth.service.AuthService;
import blossom.project.towelove.auth.strategy.USER_TYPE;
import blossom.project.towelove.auth.strategy.UserRegisterStrategy;
import blossom.project.towelove.auth.strategy.UserRegisterStrategyFactory;
import blossom.project.towelove.client.serivce.RemoteCodeService;
import blossom.project.towelove.client.serivce.RemoteUserService;
import blossom.project.towelove.common.config.thirdParty.ThirdPartyLoginConfig;
import blossom.project.towelove.common.constant.RedisKeyConstant;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.domain.dto.ThirdPartyLoginUser;
import blossom.project.towelove.common.domain.dto.UserThirdParty;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import blossom.project.towelove.common.request.auth.AuthVerifyCodeRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.SysUserVo;
import blossom.project.towelove.common.utils.JsonUtils;
import blossom.project.towelove.common.utils.ThirdPartyLoginUtil;
import blossom.project.towelove.framework.redis.service.RedisService;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.towelove.common.core.constant.HttpStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final RemoteUserService remoteUserService;

    private final RemoteCodeService remoteCodeService;

    private final RedisService redisService;

    private final RestTemplate restTemplate;

    private ThirdPartyLoginConfig thirdPartyLoginConfig;



    @Override
    public String register(@Valid AuthRegisterRequest authLoginRequest) {
        //校验手机号以及邮箱格式，校验验证码格式是否正确
        UserRegisterStrategy userRegisterStrategy = UserRegisterStrategyFactory.userRegisterStrategy(authLoginRequest.getType());
        if (Objects.isNull(userRegisterStrategy)){
            throw new ServiceException("非法请求，注册渠道错误");
        }
        //验证码校验
        if (!userRegisterStrategy.valid(authLoginRequest)) {
            throw new ServiceException("验证码校验失败，请输入正确的验证码");
        }
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
        //校验手机号以及邮箱格式，校验验证码格式是否正确
        UserRegisterStrategy userRegisterStrategy = UserRegisterStrategyFactory.userRegisterStrategy(authLoginRequest.getType());
        if (Objects.isNull(userRegisterStrategy)){
            throw new ServiceException("非法请求，注册渠道错误");
        }
        //验证码校验
        if (!userRegisterStrategy.valid(authLoginRequest)) {
            throw new ServiceException("验证码校验失败，请输入正确的验证码");
        }
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
    public String thirdPartyRegister(AuthLoginRequest authLoginRequest) {
        // 获取策略工厂中对应的注册策略
        UserRegisterStrategy userRegisterStrategy = UserRegisterStrategyFactory.userRegisterStrategy(authLoginRequest.getType());

        if (Objects.isNull(userRegisterStrategy)){
            throw new ServiceException("非法请求，注册渠道错误");
        }

        if (userRegisterStrategy == null || !userRegisterStrategy.valid(authLoginRequest)) {
            throw new ServiceException("无效的第三方登录类型或无法验证第三方登录请求");
        }

        // TODO: 看策略类的接口如何修改 这里我想到的就是使用redis来做缓存， 建议valid的返回类型 返回给到第三方登录用户信息可以避免对Redis的依赖
        // 使用code码从redis中获取第三方登录用户信息
        String json = (String) redisService.redisTemplate.opsForValue().get(RedisKeyConstant.VALIDATE_CODE+ authLoginRequest.getType() + ":" + authLoginRequest.getVerifyCode());
        ThirdPartyLoginUser thirdPartyLoginUser = JsonUtils.jsonToPojo(json, ThirdPartyLoginUser.class);

        System.err.println(JsonUtils.objectToJson(thirdPartyLoginUser));
        // 验证成功，获取或创建用户
        SysUser user = findOrCreateUser(thirdPartyLoginUser);

        // 登录本地用户并生成令牌
        StpUtil.login(user.getId());

        // 返回令牌
        return StpUtil.getTokenInfo().tokenValue;
    }

    /*实现根据第三方用户信息查询或创建本地用户的逻辑*/
    private SysUser findOrCreateUser(ThirdPartyLoginUser thirdPartyLoginUser) {
        // 检查用户是否已存在
        Result<Long> uidResult = remoteUserService.findUserIdByThirdPartyId(thirdPartyLoginUser.getSocialUid());
        log.info("调用user远程服务获取到的接口为: {}", uidResult);

        if (Objects.isNull(uidResult) && uidResult.getData() != null) {
            // 用户已存在，直接获取用户信息
            Long userId = uidResult.getData();
            Result<SysUserVo> userResult = remoteUserService.getUserById(userId);
            if (Objects.isNull(userResult) && userResult.getData() != null) {
                // 将SysUserVo转换为SysUser实体
                SysUserVo sysUserVo = userResult.getData();
                SysUser user = new SysUser();
                BeanUtils.copyProperties(sysUserVo, user);
                return user;
            } else {
                throw new ServiceException("获取用户信息失败");
            }
        } else {
            // 用户不存在，创建新用户
            SysUser user = new SysUser();
            // 设置用户的属性
            user.setNickName(thirdPartyLoginUser.getNickname());
            user.setSex(thirdPartyLoginUser.getGender());
            user.setAvatar(thirdPartyLoginUser.getFaceImg());
            user.setLoginIp(thirdPartyLoginUser.getIp());

            // 保存用户到sys_user表中
            Result<String> saveUserResult = remoteUserService.saveUser(user);
            if (Objects.isNull(saveUserResult) && saveUserResult.getData() != null) {
                user.setId(Long.parseLong(saveUserResult.getData()));

                // 创建第三方关联信息
                UserThirdParty userThirdParty = new UserThirdParty();
                userThirdParty.setUserId(user.getId());
                userThirdParty.setThirdPartyId(thirdPartyLoginUser.getSocialUid());
                userThirdParty.setProvider(thirdPartyLoginUser.getType());
                // 保存第三方信息到user_third_party表中
                remoteUserService.saveThirdPartyUser(userThirdParty);

                return user;
            } else {
                throw new ServiceException("创建用户失败");
            }
        }
    }






    @Override
    public String sendVerifyCode(AuthVerifyCodeRequest authVerifyCodeRequest) {
        if (StrUtil.isNotBlank(authVerifyCodeRequest.getPhone())){
            checkPhoneByRegex(authVerifyCodeRequest.getPhone());
            //TODO 调用远程验证码接口
            remoteCodeService.sendValidateCodeByPhone(authVerifyCodeRequest.getPhone());
        }
        else if (StrUtil.isNotBlank(authVerifyCodeRequest.getEmail())){
            checkEmailByRegex(authVerifyCodeRequest.getEmail());
            //TODO 调用远程验证码接口
            remoteCodeService.sendValidateCodeByEmail(authVerifyCodeRequest.getEmail());
        }else {
            throw new ServiceException("发送验证码失败，邮箱或手机号为空");
        }
        return "发送验证码成功";
    }


    //    public void check (AuthLoginRequest authLoginRequest){
//        String type = authLoginRequest.getType();
//        if (PHONE.type.equals(type)){
//
//        }
//        //校验验证码是否正确
//        //TODO：等待验证码
//        String phone = authLoginRequest.getPhoneNumber();
//        String email = authLoginRequest.getEmail();
//        String code = authLoginRequest.getVerifyCode();
//        if (StrUtil.isNotBlank(phone)){
//            checkPhoneByRegex(phone);
//            checkVerifyCode(phone,code);
//            return;
//        }
//        if (StrUtil.isNotBlank(email)){
//            checkEmailByRegex(email);
//            checkVerifyCode(email,code);
//            return;
//        }
//        throw new ServiceException("验证码校验失败,手机号或邮箱为空");
//    }
//    public boolean checkVerifyCode(String key,String code){
//        //TODO 等待验证码接口
//        String codeFromSystem = (String) redisService.redisTemplate.opsForValue().get(RedisKeyConstants.VALIDATE_CODE + key);
//        if (StrUtil.isBlank(codeFromSystem)){
//            throw new ServiceException("验证码校验失败，未发送验证码");
//        }
//        if (!code.equals(codeFromSystem)){
//            throw new ServiceException("验证码校验失败，验证码错误");
//        }
//        return true;
//    }
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
