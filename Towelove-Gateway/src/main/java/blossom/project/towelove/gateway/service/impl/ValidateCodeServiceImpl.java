package blossom.project.towelove.gateway.service.impl;

import blossom.project.towelove.common.constant.CacheConstants;
import blossom.project.towelove.common.constant.Constants;
import blossom.project.towelove.common.exception.CaptchaException;
import blossom.project.towelove.common.response.AjaxResult;
import blossom.project.towelove.common.utils.StringUtils;
import blossom.project.towelove.common.utils.uuid.IdUtils;
import blossom.project.towelove.framework.redis.service.RedisService;
import cn.hutool.core.codec.Base64;
import com.google.code.kaptcha.Producer;

import blossom.project.towelove.gateway.config.properties.KaptchaProperties;
import blossom.project.towelove.gateway.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author: 张锦标
 * @date: 2023/3/10 13:43
 * ValidateCodeServiceImpl类
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisService redisService;

    @Autowired
    private KaptchaProperties kaptchaProperties;

    /**
     * 生成验证码
     */
    @Override
    public AjaxResult createCaptcha() throws IOException, CaptchaException
    {
        AjaxResult ajax = AjaxResult.success();
        boolean kaptchaEnabled = kaptchaProperties.getEnabled();
        ajax.put("kaptchaEnabled", kaptchaEnabled);
        if (!kaptchaEnabled)
        {
            return ajax;
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.KAPTCHA_CODES + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        String captchaType = kaptchaProperties.getType();
        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisService.setCacheObject(verifyKey, code, 
                Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

    /**
     * 校验验证码
     */
    @Override
    public void checkCaptcha(String code, String uuid) throws CaptchaException
    {
        if (StringUtils.isEmpty(code))
        {
            throw new CaptchaException("验证码答案不能为空");
        }
        if (StringUtils.isEmpty(uuid))
        {
            throw new CaptchaException("验证码已失效");
        }
        //获取并且删除验证码在redis中的缓存
        String verifyKey = CacheConstants.KAPTCHA_CODES + uuid;
        String captcha = redisService.getCacheObject(verifyKey);
        //redisService.expire(verifyKey,10,TimeUnit.SECONDS);
        if (!code.equalsIgnoreCase(captcha))
        {
            throw new CaptchaException("验证码错误");
        }
        redisService.deleteObject(verifyKey);
    }
}
