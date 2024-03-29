package blossom.project.towelove.framework.flower.executor;

import blossom.project.towelove.framework.flower.model.BatchFlowBizContext;
import blossom.project.towelove.framework.flower.model.FlowBizContext;
import blossom.project.towelove.framework.flower.model.service.BatchFlowService;
import blossom.project.towelove.framework.flower.register.FlowServiceRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class BatchFlowTemplate extends AbstractFlowTemplate {
    private static final Logger log = LoggerFactory.getLogger(BatchFlowTemplate.class);

    public BatchFlowTemplate() {
    }

    @Override
    public void execute(FlowBizContext flowBizContext) {
        final BatchFlowBizContext batchFlowBizContext = (BatchFlowBizContext)flowBizContext;
        final List<BatchFlowService> executedList = new ArrayList();
        List<BatchFlowService> beforeList = FlowServiceRegister.buildBatchFlowServiceInstance(this.beforeExecutor);
        final List<BatchFlowService> transList = FlowServiceRegister.buildBatchFlowServiceInstance(this.executor);
        List<BatchFlowService> afterList = FlowServiceRegister.buildBatchFlowServiceInstance(this.afterExecutor);

        try {
            this.executeFlowService(beforeList, batchFlowBizContext, executedList, false);
            TransactionTemplate transactionTemplate = null;
            if (this.executorEnable) {
                try {
                    transactionTemplate = (TransactionTemplate)this.applicationContext.getBean(TransactionTemplate.class);
                } catch (NoSuchBeanDefinitionException var9) {
                    log.warn("向spring获取TransactionTemplate失败，执行无事务流程");
                }
            }

            if (transactionTemplate != null) {
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                        BatchFlowTemplate.this.executeFlowService(transList, batchFlowBizContext, executedList, false);
                    }
                });
            } else {
                this.executeFlowService(transList, batchFlowBizContext, executedList, false);
            }

            this.executeFlowService(afterList, batchFlowBizContext, executedList, true);
        } catch (Exception var10) {
            this.rollbackFlowService(executedList, batchFlowBizContext);
            throw var10;
        }
    }

    private void executeFlowService(List<BatchFlowService> batchActivities, BatchFlowBizContext batchFlowBizContext, List<BatchFlowService> executedList, boolean catchExp) {
        if (!CollectionUtils.isEmpty(batchActivities)) {
            batchActivities.forEach((batchFlowService) -> {
                executedList.add(batchFlowService);

                try {
                    batchFlowService.execute(batchFlowBizContext);
                } catch (Exception var5) {
                    if (!catchExp) {
                        throw var5;
                    }

                    log.error("执行流程节点异常, FlowService = {}", batchFlowService.getClass().getName(), var5);
                }

            });
        }
    }

    private void rollbackFlowService(List<BatchFlowService> rollbackList, BatchFlowBizContext batchFlowBizContext) {
        if (!CollectionUtils.isEmpty(rollbackList)) {
            rollbackList.forEach((iFlowService) -> {
                try {
                    iFlowService.rollback(batchFlowBizContext);
                } catch (Exception var3) {
                    log.error("执行acitivty滚回失败", var3);
                }

            });
        }
    }
}
