package blossom.project.towelove.msg.service.Impl;

import blossom.project.towelove.common.constant.RedisKeyConstant;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.msg.ValidateCodeRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.redis.service.RedisService;
import blossom.project.towelove.msg.service.ValidatedCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.msg.service.Impl
 * @className: ValidateCodeServiceImpl
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/1 21:50
 * @version: 1.0
 */
@Service
@RequiredArgsConstructor
public class ValidateCodeServiceImpl implements ValidatedCodeService {

    private final RedisService redisService;

    @Override
    public String validate( ValidateCodeRequest validateCodeRequest) {
        //判断验证码是否存在
        String codeFromSys = redisService.getCacheObject(RedisKeyConstant.VALIDATE_CODE + validateCodeRequest.getNumber());
        if (Objects.isNull(codeFromSys)){
            throw new ServiceException(validateCodeRequest.getType() + "尚未发送验证码");
        }
        //判断验证码是否正确
        if (!codeFromSys.equals(validateCodeRequest.getCode())){
            throw new ServiceException(validateCodeRequest.getType() + "验证码错误,请重新输入");
        }
        redisService.deleteObject(RedisKeyConstant.VALIDATE_CODE + validateCodeRequest.getNumber());
        return "success";
    }

    @Override
    public String validateMulti( List<ValidateCodeRequest> validateCodeRequests) {
        validateCodeRequests.forEach(validateCodeRequest -> {
            try {
                validate(validateCodeRequest);
            }catch (ServiceException e){
                throw new ServiceException(e.getMessage());
            }
        });
        return "success";
    }
}
