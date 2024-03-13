package blossom.project.towelove.framework.flower.executor;

import blossom.project.towelove.framework.flower.model.BatchFlowBizContext;
import blossom.project.towelove.framework.flower.model.FlowBizContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Iterator;
/**
 * @author: ZhangBlossom
 * @date: 2024/3/13 19:20
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
public class FlowExecutor implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public FlowExecutor() {
    }


    /**
     * 执行流程。
     *
     * @param flowCode 流程代码，不可为空。
     * @param bizCode 业务代码，不可为空。
     * @param bizTag 业务标签，可以为空。
     * @param flowBizContext 流程业务上下文，包含执行流程所需要的相关信息。
     * @throws RuntimeException 如果flowCode或bizCode为空，则抛出异常。
     */
    public void executeFlow(String flowCode, String bizCode, String bizTag, FlowBizContext flowBizContext) {
        // 检查flowCode和bizCode是否为空
        if (StringUtils.isAnyBlank(new CharSequence[]{flowCode, bizCode})) {
            throw new RuntimeException("flowCode和bizCode不能为空");
        } else {
            // 构造唯一的流程标识符
            String flowId = flowCode + ":" + bizCode + (StringUtils.isBlank(bizTag) ? "" : ":" + bizTag);
            // 尝试从应用上下文中获取对应的流程模板
            FlowTemplate flowTemplate = (FlowTemplate)this.applicationContext.getBean(flowId, FlowTemplate.class);
            if (flowTemplate != null) {
                // 设置流程上下文信息
                flowBizContext.setFlowCode(flowCode);
                flowBizContext.setBizCode(bizCode);
                flowBizContext.setBizTag(bizTag);
                // 执行流程模板
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
