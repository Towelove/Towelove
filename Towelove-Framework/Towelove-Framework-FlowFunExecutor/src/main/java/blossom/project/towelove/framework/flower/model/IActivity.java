package blossom.project.towelove.framework.flower.model;

public interface IActivity {
    void execute(FlowBizContext var1);

    default void rollback(FlowBizContext flowBizContext) {
    }
}
