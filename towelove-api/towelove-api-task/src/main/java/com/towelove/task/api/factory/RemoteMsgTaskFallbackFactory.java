package com.towelove.task.api.factory;

import com.towelove.common.core.domain.R;
import com.towelove.task.api.RemoteMsgTaskService;
import com.towelove.task.api.model.MsgTask;
import com.towelove.task.api.vo.MsgTaskSimpleRespVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:季台星
 * @Date：2023-3-12 12:10
 */
@Component
//使用下面这个注解必须保证该类的类路径被配置到
//META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
//@AutoConfiguration
public class RemoteMsgTaskFallbackFactory implements FallbackFactory<RemoteMsgTaskService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteMsgTaskFallbackFactory.class);

    @Override
    public RemoteMsgTaskService create(Throwable throwable) {
        return new RemoteMsgTaskService() {
            @Override
            public R<MsgTask> getMailAccount(Long id) {
                return R.fail("获得消息任务失败"+throwable.getMessage());
            }

            @Override
            public R<List<MsgTaskSimpleRespVO>> getSimpleMailAccountList() {
                return R.fail("获得消息任务列表失败" + throwable.getMessage());
            }
        };
    }
}
