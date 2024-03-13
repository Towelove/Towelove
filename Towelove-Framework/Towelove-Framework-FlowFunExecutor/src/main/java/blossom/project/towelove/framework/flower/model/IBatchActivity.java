package blossom.project.towelove.framework.flower.model;

public interface IBatchActivity extends IActivity {
    void execute(BatchFlowBizContext var1);

    default void rollback(BatchFlowBizContext batchFlowBizContext) {
    }

    void execute(FlowBizContext var1);

    default void rollback(FlowBizContext flowBizContext) {
    }
}
