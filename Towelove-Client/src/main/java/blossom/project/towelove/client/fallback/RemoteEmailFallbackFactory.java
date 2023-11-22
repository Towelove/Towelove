package blossom.project.towelove.client.fallback;

import blossom.project.towelove.client.serivce.RemoteEmailService;
import blossom.project.towelove.client.serivce.RemoteMsgService;
import blossom.project.towelove.common.constant.SecurityConstants;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.msg.MsgTaskPageRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
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
public class RemoteEmailFallbackFactory implements FallbackFactory<RemoteEmailService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteEmailFallbackFactory.class);

    @Override
    public RemoteEmailService create(Throwable throwable) {
        return new RemoteEmailService() {
            @Override
            public Result<String> sendValidateCode(String email) {
                return Result.fail("发送验证码失败，请稍后再发送一次", MDC.get(SecurityConstants.REQUEST_ID));
            }
        };
    }
}
