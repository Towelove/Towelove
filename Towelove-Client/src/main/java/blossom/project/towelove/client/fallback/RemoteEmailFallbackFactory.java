package blossom.project.towelove.client.fallback;

import blossom.project.towelove.client.serivce.RemoteCodeService;
import blossom.project.towelove.common.constant.SecurityConstants;
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
public class RemoteEmailFallbackFactory implements FallbackFactory<RemoteCodeService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteEmailFallbackFactory.class);

    @Override
    public RemoteCodeService create(Throwable throwable) {
        return new RemoteCodeService() {
            @Override
            public Result<String> sendValidateCodeByEmail(String email) {
                return Result.fail(null,SecurityConstants.REQUEST_ID);
            }

            @Override
            public Result<String> sendValidateCodeByPhone(String phoneNumber) {
                return Result.fail(null,SecurityConstants.REQUEST_ID);
            }
        };
    }
}
