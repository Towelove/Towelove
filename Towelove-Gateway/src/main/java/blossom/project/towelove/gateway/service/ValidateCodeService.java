package blossom.project.towelove.gateway.service;



import blossom.project.towelove.common.exception.CaptchaException;
import blossom.project.towelove.common.response.AjaxResult;

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
