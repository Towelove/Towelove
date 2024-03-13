package blossom.project.towelove.framework.flower.model.service;

import blossom.project.towelove.framework.flower.model.BatchFlowBizContext;
import blossom.project.towelove.framework.flower.model.FlowBizContext;

/**
 * @author: ZhangBlossom
 * @date: 2024/3/13 19:28
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * 可批量执行的服务
 * 批量执行时，需要实现该接口
 */
public interface BatchFlowService extends FlowSerivce {
    void execute(BatchFlowBizContext var1);

    default void rollback(BatchFlowBizContext batchFlowBizContext) {
    }

    @Override
    void execute(FlowBizContext var1);

    @Override
    default void rollback(FlowBizContext flowBizContext) {
    }
}
