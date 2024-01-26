package blossom.project.towelove.client.fallback;

import blossom.project.towelove.client.serivce.RemoteSmsService;
import blossom.project.towelove.common.constant.SecurityConstant;
import blossom.project.towelove.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Author:zhang.blossom
 * @Date：2023-11-22 14:10
 */
@Component
//@AutoConfiguration
//使用下面这个注解必须保证该类的类路径被配置到
//META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
public class RemoteSmsFallbackFactory implements FallbackFactory<RemoteSmsService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteSmsFallbackFactory.class);

    @Override
    public RemoteSmsService create(Throwable throwable) {
        return new RemoteSmsService() {
            @Override
            public Result<String> sendValidateCodeByPhone(String phoneNumber) {
                return Result.fail(null,MDC.get(SecurityConstant.REQUEST_ID));
            }
        };
    }
}
