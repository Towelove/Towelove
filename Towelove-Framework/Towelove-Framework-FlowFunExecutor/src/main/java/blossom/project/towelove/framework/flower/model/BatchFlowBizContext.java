package blossom.project.towelove.framework.flower.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchFlowBizContext extends FlowBizContext {
    protected List<FlowBizContext> flowBizContextList = new ArrayList();
    protected Map<IBatchActivity, Integer> executeIndexMap = new HashMap();

    public BatchFlowBizContext() {
    }

    public void addContext(FlowBizContext flowBizContext) {
        this.flowBizContextList.add(flowBizContext);
    }

    public List<FlowBizContext> getFlowBizContextList() {
        return (List)(this.flowBizContextList == null ? new ArrayList() : this.flowBizContextList);
    }

    public void setFlowBizContextList(List<FlowBizContext> flowBizContextList) {
        this.flowBizContextList = flowBizContextList;
    }

    public Integer getExecuteIndex(IBatchActivity batchActivity) {
        return (Integer)this.executeIndexMap.get(batchActivity);
    }

    public void setExecuteIndex(IBatchActivity batchActivity, Integer executeIndex) {
        this.executeIndexMap.put(batchActivity, executeIndex);
    }

    public Map<IBatchActivity, Integer> getExecuteIndexMap() {
        return this.executeIndexMap;
    }

    public void setExecuteIndexMap(Map<IBatchActivity, Integer> executeIndexMap) {
        this.executeIndexMap = executeIndexMap;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BatchFlowBizContext)) {
            return false;
        } else {
            BatchFlowBizContext other = (BatchFlowBizContext)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$flowBizContextList = this.getFlowBizContextList();
                Object other$flowBizContextList = other.getFlowBizContextList();
                if (this$flowBizContextList == null) {
                    if (other$flowBizContextList != null) {
                        return false;
                    }
                } else if (!this$flowBizContextList.equals(other$flowBizContextList)) {
                    return false;
                }

                Object this$executeIndexMap = this.getExecuteIndexMap();
                Object other$executeIndexMap = other.getExecuteIndexMap();
                if (this$executeIndexMap == null) {
                    if (other$executeIndexMap != null) {
                        return false;
                    }
                } else if (!this$executeIndexMap.equals(other$executeIndexMap)) {
                    return false;
                }

                return true;
            }
        }
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof BatchFlowBizContext;
    }

    @Override
    public int hashCode() {
        int PRIME = 1;
        int result = 1;
        Object $flowBizContextList = this.getFlowBizContextList();
        result = result * 59 + ($flowBizContextList == null ? 43 : $flowBizContextList.hashCode());
        Object $executeIndexMap = this.getExecuteIndexMap();
        result = result * 59 + ($executeIndexMap == null ? 43 : $executeIndexMap.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "BatchFlowBizContext(flowBizContextList=" + this.getFlowBizContextList() + ", executeIndexMap=" + this.getExecuteIndexMap() + ")";
    }
}
