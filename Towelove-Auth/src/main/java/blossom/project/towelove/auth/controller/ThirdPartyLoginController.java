package blossom.project.towelove.auth.controller;


import blossom.project.towelove.common.config.thirdParty.ThirdPartyLoginConfig;
import blossom.project.towelove.common.domain.dto.ThirdPartyLoginUser;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.utils.ThirdPartyLoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;

/**
 * @Author 苏佳
 * @Date 2023 11 08 10 22
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class ThirdPartyLoginController {

    private final RestTemplate restTemplate;

    public ThirdPartyLoginController() {
        this.restTemplate = new RestTemplate();
    }

    @Resource
    private ThirdPartyLoginConfig thirdPartyLoginConfig;
    @GetMapping("/thirdPartyLogin")
    public Result<URI> initiateLogin(@RequestParam String type) {
        System.err.println("跳转链接:"+ThirdPartyLoginUtil.initiateLogin(thirdPartyLoginConfig, restTemplate, type));
        return Result.ok(ThirdPartyLoginUtil.initiateLogin(thirdPartyLoginConfig, restTemplate, type));
    }

    @GetMapping("/callback")
    public Result<ThirdPartyLoginUser> callback(@RequestParam String type, @RequestParam String code) {
        // 获取第三方登录用户信息
        return Result.ok(ThirdPartyLoginUtil.getSocialUserInfo(thirdPartyLoginConfig, restTemplate, type, code, ThirdPartyLoginUser.class));
    }

}


