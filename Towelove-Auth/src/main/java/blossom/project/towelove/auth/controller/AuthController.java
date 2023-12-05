package blossom.project.towelove.auth.controller;

import blossom.project.towelove.auth.service.AuthService;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import blossom.project.towelove.common.request.auth.AuthVerifyCodeRequest;
import blossom.project.towelove.common.request.auth.ThirdPartyLoginRequest;
import blossom.project.towelove.common.response.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

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
     * 第三方登录
     * @param thirdPartyLoginRequest
     * @return
     */
    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody ThirdPartyLoginRequest thirdPartyLoginRequest){
        return Result.ok(authService.login(thirdPartyLoginRequest));
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
