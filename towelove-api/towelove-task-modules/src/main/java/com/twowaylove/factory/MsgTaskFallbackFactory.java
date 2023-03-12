package com.twowaylove.factory;

import com.towelove.common.core.domain.R;
import com.twowaylove.MsgTaskService;
import com.twowaylove.model.MsgTask;
import com.twowaylove.vo.MsgTaskSimpleRespVO;
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
public class MsgTaskFallbackFactory implements FallbackFactory<MsgTaskService> {
    private static final Logger log = LoggerFactory.getLogger(MsgTaskFallbackFactory.class);

    @Override
    public MsgTaskService create(Throwable throwable) {
        return new MsgTaskService() {
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
