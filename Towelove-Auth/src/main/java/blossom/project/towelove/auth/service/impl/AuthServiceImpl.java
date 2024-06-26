package blossom.project.towelove.auth.service.impl;

import blossom.project.towelove.auth.service.AuthService;
import blossom.project.towelove.auth.strategy.UserAccessStrategy;
import blossom.project.towelove.auth.strategy.UserRegisterStrategyFactory;
import blossom.project.towelove.auth.utils.VerifyCodeUtils;
import blossom.project.towelove.client.serivce.file.RemoteFileUploadService;
import blossom.project.towelove.client.serivce.msg.RemoteEmailService;
import blossom.project.towelove.client.serivce.msg.RemoteSmsService;
import blossom.project.towelove.client.serivce.msg.RemoteValidateService;
import blossom.project.towelove.client.serivce.user.RemoteUserService;
import blossom.project.towelove.common.constant.RedisKeyConstant;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.auth.*;
import blossom.project.towelove.common.request.msg.ValidateCodeRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.LoginUserResponse;
import blossom.project.towelove.framework.redis.service.RedisService;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import com.towelove.common.core.constant.HttpStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final RemoteUserService remoteUserService;

    private final RemoteEmailService remoteEmailService;

    private final RemoteSmsService remoteSmsService;

    private final RemoteValidateService remoteValidateService;

    private final RemoteFileUploadService fileUploadService;

    private final String PICTURE_TYPE = "jpg jpeg png";

    @Override
    public Result<String> register(AuthRegisterRequest authRegisterRequest) {
        Result<String> res = new Result<>();
        //校验手机号以及邮箱格式，校验验证码格式是否正确
        UserAccessStrategy userAccessStrategy = UserRegisterStrategyFactory.userAccessStrategy(authRegisterRequest.getType());
        SysUser sysUser = userAccessStrategy.register(authRegisterRequest);
        Result<SysUser> result = remoteUserService.saveUser(sysUser);
        log.info("调用user远程服务获取到的接口为: {}",result);
        if (Objects.isNull(result) || result.getCode() != com.towelove.common.core.constant.HttpStatus.SUCCESS){
            throw new ServiceException(result.getMsg());
        }
        SysUser sysUserFromSys = result.getData();
        if (StrUtil.isBlank(sysUser.getEmail()) || StrUtil.isBlank(sysUser.getPhoneNumber())){
            res.setCode(HttpStatus.CREATED);
        }
        StpUtil.login(JSON.toJSONString(sysUserFromSys));
        res.setData(StpUtil.getTokenInfo().tokenValue);
        return res;
    }

    @Override
    public Result<String> login(AuthLoginRequest authLoginRequest) {
        Result<String> res = new Result<>();
        res.setCode(HttpStatus.SUCCESS);
        //校验手机号以及邮箱格式，校验验证码格式是否正确
        UserAccessStrategy userAccessStrategy = UserRegisterStrategyFactory.userAccessStrategy(authLoginRequest.getType());
        LoginUserResponse sysUser = userAccessStrategy.login(authLoginRequest);
        StpUtil.login(JSON.toJSONString(sysUser));
        if (Objects.isNull(sysUser.getUserPermission()) || sysUser.getUserPermission().isEmpty()) {
            //需要用户补充信息，但依然是登入态
            res.setCode(HttpStatus.CREATED);
            res.setMsg("用户需要完善信息");
        }
        String tokenValue = StpUtil.getTokenInfo().tokenValue;
        if (StrUtil.isBlank(tokenValue)){
            throw new ServiceException("An exception occurred while generating the token...");
        }
        res.setData(tokenValue);
        return res;
    }
//
//    /**
//     * 第三方用户登入
//     *
//     * @param authLoginRequest
//     * @return
//     */
//
//    @Override
//    public Result<String> thirdPartyRegister(ThirdPartyAccessRequest thirdPartyAccessRequest) {
//        Result<String> res = new Result<>();
//        // 获取策略工厂中对应的注册策略
//        UserAccessStrategy userAccessStrategy = UserRegisterStrategyFactory.userAccessStrategy(thirdPartyAccessRequest.getType());
//
//        SysUser sysUser = userAccessStrategy.login(authLoginRequest);
//        StpUtil.login(sysUser.getId());
//        if (StrUtil.isBlank(sysUser.getPassword())) {
//            res.setCode(HttpStatus.CREATED);
//            res.setMsg("第三方登入用户未完善信息");
//        }
//        res.setData(StpUtil.getTokenInfo().tokenValue);
////        if (StrUtil.isBlank(token)) {
////            throw new ServiceException("无效的第三方登录类型或无法验证第三方登录请求");
////        }
//        // TODO: 看策略类的接口如何修改 这里我想到的就是使用redis来做缓存， 建议valid的返回类型 返回给到第三方登录用户信息可以避免对Redis的依赖
//        // 使用code码从redis中获取第三方登录用户信息
////        String json = (String) redisService.redisTemplate.opsForValue().get(RedisKeyConstant.VALIDATE_CODE+ authLoginRequest.getType() + ":" + authLoginRequest.getVerifyCode());
////        ThirdPartyLoginUser thirdPartyLoginUser = JsonUtils.jsonToPojo(json, ThirdPartyLoginUser.class);
////
////        System.err.println(JsonUtils.objectToJson(thirdPartyLoginUser));
////        // 验证成功，获取或创建用户
////        SysUser user = findOrCreateUser(thirdPartyLoginUser);
////
////        // 登录本地用户并生成令牌
////        StpUtil.login(user.getId());
//
//        // 返回令牌
//        return res;
//    }

    @Override
    public String sendVerifyCode(AuthVerifyCodeRequest authVerifyCodeRequest) {
        if (StrUtil.isBlank(authVerifyCodeRequest.getEmail()) && StrUtil.isBlank(authVerifyCodeRequest.getPhone())){
            throw new ServiceException("发送验证码失败，邮箱或手机号为空");
        }
        if (StrUtil.isNotBlank(authVerifyCodeRequest.getPhone())) {
            checkPhoneByRegex(authVerifyCodeRequest.getPhone());
            Result<String> result = remoteSmsService.sendValidateCodeByPhone(authVerifyCodeRequest.getPhone());
            if (Objects.isNull(result) || HttpStatus.SUCCESS != result.getCode()){
                throw new ServiceException(result.getMsg());
            }
        } else {
            checkEmailByRegex(authVerifyCodeRequest.getEmail());
            Result<String> result = remoteEmailService.sendValidateCodeByEmail(authVerifyCodeRequest.getEmail());
            if (Objects.isNull(result) || HttpStatus.SUCCESS != result.getCode()){
                throw new ServiceException(result.getMsg());
            }
        }
        return "发送验证码成功";
    }


    @Override
    public String restockUserInfo(@Validated RestockUserInfoRequest restockUserInfoRequest) {
        Long id = validateCode(restockUserInfoRequest);
        restockUserInfoRequest.setId(id);
        //调用远程服务更新用户信息
        RestockUserInfoDTO restockUserInfoDTO = RestockUserInfoDTO.builder()
                .Id(restockUserInfoRequest.getId())
                .avatar(restockUserInfoRequest.getAvatar())
                .sex(restockUserInfoRequest.getSex())
                .nickName(restockUserInfoRequest.getNickName())
                .phoneNumber(restockUserInfoRequest.getPhone())
                .email(restockUserInfoRequest.getEmail())
                .build();
        Result<LoginUserResponse> result = remoteUserService.restockUserInfo(restockUserInfoDTO);
        if (Objects.isNull(result) || HttpStatus.SUCCESS != result.getCode()){
            throw new ServiceException("补充用户信息失败");
        }
        StpUtil.login(JSON.toJSONString(result.getData()));
        return StpUtil.getTokenValue();
    }

    /**
     * 处理校验验证码
     * @param restockUserInfoRequest
     * @return
     */
    private Long validateCode(RestockUserInfoRequest restockUserInfoRequest) {
        LoginUserResponse sysUser = JSON.parseObject(StpUtil.getLoginIdAsString(), LoginUserResponse.class);
        ValidateCodeRequest validateCodeRequests = null;
        if (StrUtil.isNotBlank(sysUser.getEmail()) && StrUtil.isNotBlank(sysUser.getPhoneNumber())){
            throw new ServiceException("请求非法！你是哪来的猴子");
        }
        if (StrUtil.isNotBlank(sysUser.getEmail())){
            //查看邮箱是否被使用

//            restockUserInfoRequest.setEmail(sysUser.getEmail());
            //需要补充手机号信息
            String phone = restockUserInfoRequest.getPhone();
            checkPhoneByRegex(phone);
            Result<Boolean> byEmailOrPhone = remoteUserService.findByEmailOrPhone(phone, null);
            if (Objects.isNull(byEmailOrPhone) || byEmailOrPhone.getData()){
                throw new ServiceException("手机号已经被绑定，请使用其他手机号");
            }
            validateCodeRequests = ValidateCodeRequest
                    .builder()
                    .number(phone)
                    .code(restockUserInfoRequest.getPhoneVerifyCode())
                    .type("phone")
                    .build();
        }else {
            String email = restockUserInfoRequest.getEmail();
            checkEmailByRegex(email);
            Result<Boolean> byEmailOrPhone = remoteUserService.findByEmailOrPhone(null, email);
            if (Objects.isNull(byEmailOrPhone) || byEmailOrPhone.getData()){
                throw new ServiceException("邮箱已经被绑定，请使用其他邮箱号");
            }
            validateCodeRequests = ValidateCodeRequest
                    .builder()
                    .number(email)
                    .code(restockUserInfoRequest.getEmailVerifyCode())
                    .type("email")
                    .build();
        }
        Result<String> validateMulti = remoteValidateService.validate(validateCodeRequests);
        if (Objects.isNull(validateMulti) || HttpStatus.SUCCESS != validateMulti.getCode()){
            throw new ServiceException(validateMulti.getMsg());
        }
        return sysUser.getId();
    }


    @Override
    public String uploadAvatar(MultipartFile file) {
        //判断如果不是图片类型全部拒绝
        if (!isPicture(file)){
            throw new ServiceException("只支持" + PICTURE_TYPE  + "类型文件");
        }
        Result<String> result = null;
        try {
            result = fileUploadService.upload(file);
            if (Objects.isNull(result) || HttpStatus.SUCCESS != result.getCode()){
                log.info("上传头像失败,失败原因为:{}",result.getMsg());
                throw new ServiceException("上传头像失败");
            }
        } catch (Exception e) {
            throw new ServiceException("上传头像失败");
        }
        return result.getData();
    }

    @Override
    public String logout() {
        try {
            StpUtil.logout();
        } catch (Exception e) {
            log.error("退出登入失败");
            return "fail";
        }
        return "success";
    }

    private boolean isPicture(MultipartFile file) {
        String suffix = FileNameUtil.getSuffix(file.getOriginalFilename());
        if (suffix != null) {
            return PICTURE_TYPE.contains(suffix);
        }
        return false;
    }

    private List<ValidateCodeRequest> buildCheckValidateRequests(String email, String emailVerifyCode, String phone, String phoneVerifyCode) {
        return List.of(ValidateCodeRequest.builder().number(email).code(emailVerifyCode).type("email").build()
                ,ValidateCodeRequest.builder().number(phone).code(phoneVerifyCode).type("phone").build());
    }

    public void checkPhoneByRegex(String phone) {
        if (!ReUtil.isMatch(RegexPool.MOBILE, phone)) {
            throw new ServiceException("手机号格式错误");
        }
    }

    public void checkEmailByRegex(String email) {
        if (!ReUtil.isMatch(RegexPool.EMAIL, email)) {
            throw new ServiceException("邮箱格式错误");
        }
    }
    /*实现根据第三方用户信息查询或创建本地用户的逻辑*/
//    private SysUser findOrCreateUser(ThirdPartyLoginUser thirdPartyLoginUser) {
//        // 检查用户是否已存在
//        Result<Long> uidResult = remoteUserService.findUserIdByThirdPartyId(thirdPartyLoginUser.getSocialUid());
//        log.info("调用user远程服务获取到的接口为: {}", uidResult);
//
//        if (Objects.isNull(uidResult) && uidResult.getData() != null) {
//            // 用户已存在，直接获取用户信息
//            Long userId = uidResult.getData();
//            Result<SysUserVo> userResult = remoteUserService.getUserById(userId);
//            if (Objects.isNull(userResult) && userResult.getData() != null) {
//                // 将SysUserVo转换为SysUser实体
//                SysUserVo sysUserVo = userResult.getData();
//                SysUser user = new SysUser();
//                BeanUtils.copyProperties(sysUserVo, user);
//                return user;
//            } else {
//                throw new ServiceException("获取用户信息失败");
//            }
//        } else {
//            // 用户不存在，创建新用户
//            SysUser user = new SysUser();
//            // 设置用户的属性
//            user.setNickName(thirdPartyLoginUser.getNickname());
//            user.setSex(thirdPartyLoginUser.getGender());
//            user.setAvatar(thirdPartyLoginUser.getFaceImg());
//            user.setLoginIp(thirdPartyLoginUser.getIp());
//
//            // 保存用户到sys_user表中
//            Result<String> saveUserResult = remoteUserService.saveUser(user);
//            if (Objects.isNull(saveUserResult) && saveUserResult.getData() != null) {
//                user.setId(Long.parseLong(saveUserResult.getData()));
//
//                // 创建第三方关联信息
//                UserThirdParty userThirdParty = new UserThirdParty();
//                userThirdParty.setUserId(user.getId());
//                userThirdParty.setThirdPartyId(thirdPartyLoginUser.getSocialUid());
//                userThirdParty.setProvider(thirdPartyLoginUser.getType());
//                // 保存第三方信息到user_third_party表中
//                remoteUserService.saveThirdPartyUser(userThirdParty);
//
//                return user;
//            } else {
//                throw new ServiceException("创建用户失败");
//            }

//        }

//    }


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
}
