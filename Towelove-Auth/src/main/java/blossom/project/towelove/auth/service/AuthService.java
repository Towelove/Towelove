package blossom.project.towelove.auth.service;

import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import blossom.project.towelove.common.request.auth.AuthVerifyCodeRequest;

import javax.validation.Valid;

public interface AuthService {
    String register(@Valid AuthRegisterRequest authLoginRequest);

    String login(AuthLoginRequest authLoginRequest);

    String sendVerifyCode(AuthVerifyCodeRequest authVerifyCodeRequest);
}
