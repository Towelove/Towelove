package blossom.project.towelove.framework.flower.model.service;

import blossom.project.towelove.framework.flower.model.BatchFlowBizContext;
import blossom.project.towelove.framework.flower.model.FlowBizContext;

/**
 * @author: ZhangBlossom
 * @date: 2024/3/12 14:28
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * 还在开发ing
 *
 */
public interface CanBatchFlowService extends BatchFlowService {
    @Override
    default void execute(BatchFlowBizContext batchFlowBizContext) {
        for(int n = 0; n < batchFlowBizContext.getFlowBizContextList().size(); ++n) {
            batchFlowBizContext.setExecuteIndex(this, n);
            FlowBizContext flowBizContext = (FlowBizContext)batchFlowBizContext.getFlowBizContextList().get(n);
            this.execute(flowBizContext);
        }

    }

    @Override
    default void rollback(BatchFlowBizContext batchFlowBizContext) {
        Integer index = batchFlowBizContext.getExecuteIndex(this);
        if (index != null) {
            for(int n = index; n >= 0; --n) {
                FlowBizContext flowBizContext = (FlowBizContext)batchFlowBizContext.getFlowBizContextList().get(n);
                this.rollback(flowBizContext);
            }

        }
    }

    @Override
    void execute(FlowBizContext var1);

    @Override
    default void rollback(FlowBizContext flowBizContext) {
    }
}
