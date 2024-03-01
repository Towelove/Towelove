package blossom.project.towelove.client.fallback;

import blossom.project.towelove.client.serivce.msg.RemoteValidateService;
import blossom.project.towelove.common.constant.SecurityConstant;
import blossom.project.towelove.common.request.msg.ValidateCodeRequest;
import blossom.project.towelove.common.response.Result;
import org.slf4j.MDC;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.client.fallback
 * @className: RemoteValidateFallbackFactory
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/1 22:03
 * @version: 1.0
 */
@Component
public class RemoteValidateFallbackFactory implements FallbackFactory<RemoteValidateService> {
    @Override
    public RemoteValidateService create(Throwable cause) {
        return new RemoteValidateService() {
            @Override
            public Result<String> validate(ValidateCodeRequest request) {
                return Result.fail("验证码校验失败", MDC.get(SecurityConstant.REQUEST_ID));
            }

            @Override
            public Result<String> validateMulti(List<ValidateCodeRequest> validateCodeRequests) {
                return Result.fail("验证码校验失败", MDC.get(SecurityConstant.REQUEST_ID));
            }
        };
    }
}
