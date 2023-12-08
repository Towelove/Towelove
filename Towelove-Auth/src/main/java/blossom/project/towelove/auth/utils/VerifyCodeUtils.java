package blossom.project.towelove.auth.utils;

import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.framework.redis.service.RedisService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.auth.utils
 * @className: VerifyCodeUtils
 * @author: Link Ji
 * @description: GOGO
 * @date: 2023/12/8 12:32
 * @version: 1.0
 */
@Component
public class VerifyCodeUtils {
    @Resource
    private RedisService redisService;

    public boolean valid(String codeType,String codeSource,String verifyCodeFromUser){
        Object verifyCode = redisService.getCacheObject(codeType + codeSource);
        return Objects.nonNull(verifyCode) && verifyCodeFromUser.equals(verifyCode.toString());
    }
}
