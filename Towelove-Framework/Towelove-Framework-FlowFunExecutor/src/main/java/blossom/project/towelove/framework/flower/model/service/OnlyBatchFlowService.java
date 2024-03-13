package blossom.project.towelove.framework.flower.model.service;

import blossom.project.towelove.framework.flower.model.BatchFlowBizContext;
import blossom.project.towelove.framework.flower.model.FlowBizContext;

/**
 * @author: ZhangBlossom
 * @date: 2024/3/13 13:20
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
public interface OnlyBatchFlowService extends BatchFlowService {
    @Override
    void execute(BatchFlowBizContext var1);

    @Override
    default void rollback(BatchFlowBizContext batchFlowBizContext) {
    }

    @Override
    default void execute(FlowBizContext flowBizContext) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void rollback(FlowBizContext flowBizContext) {
        throw new UnsupportedOperationException();
    }
}
