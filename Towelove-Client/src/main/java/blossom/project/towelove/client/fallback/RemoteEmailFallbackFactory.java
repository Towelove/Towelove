package blossom.project.towelove.client.fallback;

import blossom.project.towelove.client.serivce.msg.RemoteEmailService;
import blossom.project.towelove.common.request.todoList.TodoRemindRequest;
import blossom.project.towelove.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            public Result<String> sendValidateCodeByEmail(String email) {
                //TODO 邮件验证码发送失败
                //按理应该将当前消息放入到mq或者mysql
                //然后过段时间进行重试
                return Result.ok("发送验证码失败，过段时间重试");
            }

            @Override
            public Result<String> todoRemindByEmail(TodoRemindRequest request) {
                //同上
                return Result.ok("提醒消息发送失败，过段时间重试");
            }
        };
    }
}
