package blossom.project.towelove.framework.flower.executor;

import blossom.project.towelove.framework.flower.model.FlowBizContext;
import blossom.project.towelove.framework.flower.model.service.FlowSerivce;
import blossom.project.towelove.framework.flower.register.FlowServiceRegister;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * @author: ZhangBlossom
 * @date: 2024/1/23 12:20
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 * 流水线函数执行器模版
 * 存储了实际要执行的方法类，并实际进行执行
 */
@Data
public class FlowTemplate extends AbstractFlowTemplate {
    private static final Logger log = LoggerFactory.getLogger(FlowTemplate.class);

    public FlowTemplate() {
    }

    @Override
    public void execute(final FlowBizContext flowBizContext) {
        final List<FlowSerivce> executedList = new ArrayList();
        List<FlowSerivce> beforeList = FlowServiceRegister.buildFlowServiceInstance(this.beforeExecutor);
        final List<FlowSerivce> transList = FlowServiceRegister.buildFlowServiceInstance(this.executor);
        List<FlowSerivce> afterList = FlowServiceRegister.buildFlowServiceInstance(this.afterExecutor);

        try {
            this.executeFlowService(beforeList, flowBizContext, executedList, false);
            TransactionTemplate transactionTemplate = null;
            if (this.executorEnable) {
                try {
                    transactionTemplate = (TransactionTemplate)this.applicationContext.getBean(TransactionTemplate.class);
                } catch (NoSuchBeanDefinitionException var8) {
                    log.warn("向spring获取TransactionTemplate失败，执行无事务流程");
                }
            }

            if (transactionTemplate != null) {
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                        FlowTemplate.this.executeFlowService(transList, flowBizContext, executedList, false);
                    }
                });
            } else {
                this.executeFlowService(transList, flowBizContext, executedList, false);
            }

            this.executeFlowService(afterList, flowBizContext, executedList, true);
        } catch (Exception var9) {
            this.rollbackFlowService(executedList, flowBizContext);
            throw var9;
        }
    }

    private void executeFlowService(List<FlowSerivce> activities, FlowBizContext flowBizContext, List<FlowSerivce> executedList, boolean catchExp) {
        if (!CollectionUtils.isEmpty(activities)) {
            activities.forEach((iFlowService) -> {
                executedList.add(iFlowService);

                try {
                    iFlowService.execute(flowBizContext);
                } catch (Exception var5) {
                    if (!catchExp) {
                        throw var5;
                    }

                    log.error("执行流程节点异常, FlowService = {}", iFlowService.getClass().getName(), var5);
                }

            });
        }
    }

    private void rollbackFlowService(List<FlowSerivce> rollbackList, FlowBizContext flowBizContext) {
        if (!CollectionUtils.isEmpty(rollbackList)) {
            rollbackList.forEach((iFlowService) -> {
                try {
                    iFlowService.rollback(flowBizContext);
                } catch (Exception var3) {
                    log.error("执行acitivty滚回失败", var3);
                }

            });
        }
    }
}
