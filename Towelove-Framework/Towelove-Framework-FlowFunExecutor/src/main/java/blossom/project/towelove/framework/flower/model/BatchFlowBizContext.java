package blossom.project.towelove.framework.flower.model;

import blossom.project.towelove.framework.flower.model.service.BatchFlowService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ZhangBlossom
 * @date: 2024/3/12 14:28
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 *
 */
@Data
@AllArgsConstructor
public class BatchFlowBizContext extends FlowBizContext {
    protected List<FlowBizContext> flowBizContextList = new ArrayList();
    protected Map<BatchFlowService, Integer> executeIndexMap = new HashMap();

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

    public Integer getExecuteIndex(BatchFlowService batchActivity) {
        return (Integer)this.executeIndexMap.get(batchActivity);
    }

    public void setExecuteIndex(BatchFlowService batchActivity, Integer executeIndex) {
        this.executeIndexMap.put(batchActivity, executeIndex);
    }

    public Map<BatchFlowService, Integer> getExecuteIndexMap() {
        return this.executeIndexMap;
    }

    public void setExecuteIndexMap(Map<BatchFlowService, Integer> executeIndexMap) {
        this.executeIndexMap = executeIndexMap;
    }

}
