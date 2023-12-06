package blossom.project.towelove.auth.controller;

import blossom.project.towelove.auth.service.AuthService;
import blossom.project.towelove.common.config.thirdParty.ThirdPartyLoginConfig;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import blossom.project.towelove.common.request.auth.AuthVerifyCodeRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.utils.ThirdPartyLoginUtil;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final RestTemplate restTemplate;


    @Resource
    private ThirdPartyLoginConfig thirdPartyLoginConfig;

    /**
     * 注册
     * @param authRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public Result<String> register( @RequestBody AuthRegisterRequest authRegisterRequest){
        return Result.ok(authService.register(authRegisterRequest));
    }

    /**
     * 登入
     * @param authLoginRequest
     * @return
     */
    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody AuthLoginRequest authLoginRequest){
        return Result.ok(authService.login(authLoginRequest));
    }

    /**
     * 获取跳转登录地址
     * @param authLoginRequest
     * @return
     */
    @PostMapping("/thirdParty")
    public Result<URI> ThirdParty(@Valid @RequestBody AuthLoginRequest authLoginRequest){
        System.err.println("跳转链接:"+ ThirdPartyLoginUtil.initiateLogin(thirdPartyLoginConfig,
                                                                        restTemplate,
                                                                        authLoginRequest.getType()));
        return Result.ok(ThirdPartyLoginUtil.initiateLogin(thirdPartyLoginConfig, restTemplate, authLoginRequest.getType()));
    }

    /**
     * 获取第三方用户信息
     * @param authLoginRequest
     * @return
     */
    @PostMapping("/thirdPartyRegisterCallback")
    public Result<String> ThirdPartyRegisterCallback(@Valid @RequestBody AuthLoginRequest authLoginRequest){
        return Result.ok(authService.thirdPartyRegister(authLoginRequest));
    }



    /**
     * 发送验证码
     * @param authVerifyCodeRequest
     * @return
     */
    @PostMapping("/send-code")
    public Result<String> sendVerifyCode(@RequestBody AuthVerifyCodeRequest authVerifyCodeRequest){
        return Result.ok(authService.sendVerifyCode(authVerifyCodeRequest));
    }
}
