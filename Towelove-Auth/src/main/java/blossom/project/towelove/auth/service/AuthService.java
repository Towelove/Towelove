package blossom.project.towelove.auth.service;

import blossom.project.towelove.common.request.auth.*;
import blossom.project.towelove.common.response.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;

public interface AuthService {
    Result<String> register(AuthRegisterRequest authLoginRequest);

    Result<String> login(AuthLoginRequest authLoginRequest);

    String sendVerifyCode(AuthVerifyCodeRequest authVerifyCodeRequest);


    String restockUserInfo(RestockUserInfoRequest restockUserInfoRequest);

    String uploadAvatar(MultipartFile file);
}
