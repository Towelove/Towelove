package blossom.project.towelove.msg.service.Impl;

import blossom.project.towelove.common.constant.RedisKeyConstant;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.utils.CodeGeneratorUtil;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.framework.redis.service.RedisService;
import blossom.project.towelove.msg.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/22 21:09
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * SmsServiceImpl类
 */
@Service
@RequiredArgsConstructor
@LoveLog
@Slf4j
public class SmsServiceImpl implements SmsService {
    private final RedisService redisService;
    @Override
    public String sendValidateCode(String phoneNumber) {
        //查询是否已经存在验证码
        if (Objects.nonNull(redisService.getCacheObject(RedisKeyConstant.VALIDATE_CODE + phoneNumber))){
            throw new ServiceException("请勿重复发送验证码");
        }
//        String code = CodeGeneratorUtil.generateFourDigitCode();
        String code = "1234";
        try {
            redisService.setCacheObject(RedisKeyConstant.VALIDATE_CODE + phoneNumber, code,5L,TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new ServiceException("发送邀请码失败");
        }
        log.info("{}发送验证码成功：{}",phoneNumber,code);
        return "发送邀请码成功";
    }
}
