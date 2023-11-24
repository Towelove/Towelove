package blossom.project.towelove.client.fallback;

import blossom.project.towelove.client.serivce.RemoteMsgService;
import blossom.project.towelove.common.constant.SecurityConstant;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.msg.MsgTaskPageRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
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
public class RemoteMsgFallbackFactory implements FallbackFactory<RemoteMsgService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteMsgFallbackFactory.class);

    @Override
    public RemoteMsgService create(Throwable throwable) {
        return new RemoteMsgService() {

            @Override
            public Result<MsgTaskResponse> getMsgTaskById(Long msgTaskId) {
                return Result.fail(null, SecurityConstant.REQUEST_ID);
            }

            @Override
            public Result<PageResponse<MsgTaskResponse>> pageQueryMsgTask(MsgTaskPageRequest requestParam) {
                return Result.fail(null, SecurityConstant.REQUEST_ID);
            }
        };
    }
}
