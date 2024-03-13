package blossom.project.towelove.framework.flower.executor;

import blossom.project.towelove.framework.flower.executor.AbstractFlowTemplate;
import blossom.project.towelove.framework.flower.model.BatchFlowBizContext;
import blossom.project.towelove.framework.flower.model.FlowBizContext;
import blossom.project.towelove.framework.flower.model.IBatchActivity;
import blossom.project.towelove.framework.flower.register.ActivityRegister;
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
        final List<IBatchActivity> executedList = new ArrayList();
        List<IBatchActivity> beforeList = ActivityRegister.buildBatchActivityInstance(this.beforeTrans);
        final List<IBatchActivity> transList = ActivityRegister.buildBatchActivityInstance(this.trans);
        List<IBatchActivity> afterList = ActivityRegister.buildBatchActivityInstance(this.afterTrans);

        try {
            this.executeActivity(beforeList, batchFlowBizContext, executedList, false);
            TransactionTemplate transactionTemplate = null;
            if (this.transEnable) {
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
                        BatchFlowTemplate.this.executeActivity(transList, batchFlowBizContext, executedList, false);
                    }
                });
            } else {
                this.executeActivity(transList, batchFlowBizContext, executedList, false);
            }

            this.executeActivity(afterList, batchFlowBizContext, executedList, true);
        } catch (Exception var10) {
            this.rollbackActivity(executedList, batchFlowBizContext);
            throw var10;
        }
    }

    private void executeActivity(List<IBatchActivity> batchActivities, BatchFlowBizContext batchFlowBizContext, List<IBatchActivity> executedList, boolean catchExp) {
        if (!CollectionUtils.isEmpty(batchActivities)) {
            batchActivities.forEach((batchActivity) -> {
                executedList.add(batchActivity);

                try {
                    batchActivity.execute(batchFlowBizContext);
                } catch (Exception var5) {
                    if (!catchExp) {
                        throw var5;
                    }

                    log.error("执行流程节点异常, activity = {}", batchActivity.getClass().getName(), var5);
                }

            });
        }
    }

    private void rollbackActivity(List<IBatchActivity> rollbackList, BatchFlowBizContext batchFlowBizContext) {
        if (!CollectionUtils.isEmpty(rollbackList)) {
            rollbackList.forEach((iActivity) -> {
                try {
                    iActivity.rollback(batchFlowBizContext);
                } catch (Exception var3) {
                    log.error("执行acitivty滚回失败", var3);
                }

            });
        }
    }
}
