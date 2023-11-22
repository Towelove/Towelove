package blossom.project.towelove.auth.controller;

import blossom.project.towelove.auth.service.AuthService;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import blossom.project.towelove.common.request.auth.AuthVerifyCodeRequest;
import blossom.project.towelove.common.response.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody AuthRegisterRequest authRegisterRequest){
        return Result.ok(authService.register(authRegisterRequest));
    }
    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody AuthLoginRequest authLoginRequest){
        return Result.ok(authService.login(authLoginRequest));
    }
    @PostMapping("/send-code")
    public Result<String> sendVerifyCode(@RequestBody AuthVerifyCodeRequest authVerifyCodeRequest){
        return Result.ok(authService.sendVerifyCode(authVerifyCodeRequest));
    }
}
