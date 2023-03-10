package com.towelove.gateway.service;


import com.towelove.common.core.exception.CaptchaException;
import com.towelove.common.core.web.domain.AjaxResult;

import java.io.IOException;

/**
 * 验证码处理
 * @author: 张锦标
 * @date: 2023/3/10 13:43
 */
public interface ValidateCodeService
{
    /**
     * 生成验证码
     */
    public AjaxResult createCaptcha() throws IOException, CaptchaException;

    /**
     * 校验验证码
     */
    public void checkCaptcha(String key, String value) throws CaptchaException;
}
