package blossom.project.towelove.msg.service.Impl;

import blossom.project.towelove.framework.redis.service.RedisService;
import blossom.project.towelove.msg.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
public class SmsServiceImpl implements SmsService {
    private final RedisService redisService;
    @Override
    public String sendValidateCode(String phoneNumber) {
        return null;
    }
}
