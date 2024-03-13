package blossom.project.towelove.framework.flower.model;

public interface ICanBatchActivity extends IBatchActivity {
    default void execute(BatchFlowBizContext batchFlowBizContext) {
        for(int n = 0; n < batchFlowBizContext.getFlowBizContextList().size(); ++n) {
            batchFlowBizContext.setExecuteIndex(this, n);
            FlowBizContext flowBizContext = (FlowBizContext)batchFlowBizContext.getFlowBizContextList().get(n);
            this.execute(flowBizContext);
        }

    }

    default void rollback(BatchFlowBizContext batchFlowBizContext) {
        Integer index = batchFlowBizContext.getExecuteIndex(this);
        if (index != null) {
            for(int n = index; n >= 0; --n) {
                FlowBizContext flowBizContext = (FlowBizContext)batchFlowBizContext.getFlowBizContextList().get(n);
                this.rollback(flowBizContext);
            }

        }
    }

    void execute(FlowBizContext var1);

    default void rollback(FlowBizContext flowBizContext) {
    }
}
