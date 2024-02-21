package blossom.project.towelove.auth.controller;

import blossom.project.towelove.auth.service.AuthService;
import blossom.project.towelove.auth.thirdParty.ThirdPartyLoginConfig;
import blossom.project.towelove.common.domain.dto.ThirdPartyLoginUser;
import blossom.project.towelove.common.request.auth.*;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.auth.thirdParty.ThirdPartyLoginUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class AuthController {

    private final AuthService authService;
    private final RestTemplate restTemplate;

    private final Logger log = LoggerFactory.getLogger(AuthService.class);


    @Resource
    private ThirdPartyLoginConfig thirdPartyLoginConfig;

    /**
     * 注册
     * @param authRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public Result<String> register( @Validated @RequestBody AuthRegisterRequest authRegisterRequest){
        return authService.register(authRegisterRequest);
    }

    /**
     * 登入
     * @param authLoginRequest
     * @return
     */
    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody AuthLoginRequest authLoginRequest){
        return authService.login(authLoginRequest);
    }

    /**
     * 获取跳转登录地址
     * @param authLoginRequest
     * @return
     */
    @PostMapping("/thirdParty")
    public Result<URI> ThirdParty(@Valid @RequestBody AuthLoginRequest authLoginRequest){
        URI uri = ThirdPartyLoginUtil.initiateLogin(thirdPartyLoginConfig, restTemplate, authLoginRequest.getType());
        //后续只需要返回后缀，前缀由前端保管即可
        log.info("第三方跳转登入类型为：[{}],跳转链接为：[{}]",authLoginRequest.getType(),uri);
        return Result.ok(uri);
    }

//    /**
//     * 获取第三方用户信息
//     * @param thirdPartyAccessRequest
//     * @return
//     */
//    @PostMapping("/thirdPartyRegisterCallback")
//    public Result<String> ThirdPartyRegisterCallback(@Valid @RequestBody ThirdPartyAccessRequest thirdPartyAccessRequest){
//        return authService.thirdPartyRegister(thirdPartyAccessRequest);
//    }

    /**
     * 发送验证码
     * @param authVerifyCodeRequest
     * @return
     */
    @PostMapping("/send-code")
    public Result<String> sendVerifyCode(@RequestBody AuthVerifyCodeRequest authVerifyCodeRequest){
        return Result.ok(authService.sendVerifyCode(authVerifyCodeRequest));
    }

    /**
     * 补充用户信息
     * @param restockUserInfoRequest
     * @return
     */
    @PostMapping("/restock-info")
    public Result<?> restockUserInfo(@Valid @RequestBody RestockUserInfoRequest restockUserInfoRequest){
        return Result.ok(authService.restockUserInfo(restockUserInfoRequest));
    }
}
