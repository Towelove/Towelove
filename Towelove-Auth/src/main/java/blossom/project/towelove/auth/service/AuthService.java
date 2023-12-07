package blossom.project.towelove.auth.service;

import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import blossom.project.towelove.common.request.auth.AuthVerifyCodeRequest;
import blossom.project.towelove.common.request.auth.RestockUserInfoRequest;
import blossom.project.towelove.common.response.Result;

import javax.validation.Valid;
import java.net.URI;

public interface AuthService {
    String register(@Valid AuthRegisterRequest authLoginRequest);

    Result<String> login(AuthLoginRequest authLoginRequest);
    Result<String> thirdPartyRegister(AuthLoginRequest authLoginRequest);

    String sendVerifyCode(AuthVerifyCodeRequest authVerifyCodeRequest);


    String restockUserInfo(RestockUserInfoRequest restockUserInfoRequest);

}
