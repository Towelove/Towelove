package blossom.project.towelove.framework.flower.executor;

import blossom.project.towelove.framework.flower.model.BatchFlowBizContext;
import blossom.project.towelove.framework.flower.model.FlowBizContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Iterator;

public class FlowExecutor implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public FlowExecutor() {
    }

    public void executeFlow(String flowCode, String bizCode, String bizTag, FlowBizContext flowBizContext) {
        if (StringUtils.isAnyBlank(new CharSequence[]{flowCode, bizCode})) {
            throw new RuntimeException("flowCode和bizCode不能为空");
        } else {
            String flowId = flowCode + ":" + bizCode + (StringUtils.isBlank(bizTag) ? "" : ":" + bizTag);
            FlowTemplate flowTemplate = (FlowTemplate)this.applicationContext.getBean(flowId, FlowTemplate.class);
            if (flowTemplate != null) {
                flowBizContext.setFlowCode(flowCode);
                flowBizContext.setBizCode(bizCode);
                flowBizContext.setBizTag(bizTag);
                flowTemplate.execute(flowBizContext);
            }
        }
    }

    public void batchExecuteFlow(String flowCode, String bizCode, String bizTag, BatchFlowBizContext batchFlowBizContext) {
        if (StringUtils.isAnyBlank(new CharSequence[]{flowCode, bizCode})) {
            throw new RuntimeException("flowCode和bizCode不能为空");
        } else {
            String flowId = flowCode + ":" + bizCode + (StringUtils.isBlank(bizTag) ? "" : ":" + bizTag);
            BatchFlowTemplate batchFlowTemplate = (BatchFlowTemplate)this.applicationContext.getBean(flowId, BatchFlowTemplate.class);
            if (batchFlowTemplate != null) {
                Iterator var7 = batchFlowBizContext.getFlowBizContextList().iterator();

                while(var7.hasNext()) {
                    FlowBizContext flowBizContext = (FlowBizContext)var7.next();
                    flowBizContext.setFlowCode(flowCode);
                    flowBizContext.setBizCode(bizCode);
                    flowBizContext.setBizTag(bizTag);
                }

                batchFlowTemplate.execute(batchFlowBizContext);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
