package blossom.project.towelove.framework.flower.executor;

import blossom.project.towelove.framework.flower.model.FlowBizContext;
import blossom.project.towelove.framework.flower.model.IActivity;
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
/**
 * @author: ZhangBlossom
 * @date: 2024/1/23 12:20
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
public class FlowTemplate extends AbstractFlowTemplate {
    private static final Logger log = LoggerFactory.getLogger(FlowTemplate.class);

    public FlowTemplate() {
    }

    @Override
    public void execute(final FlowBizContext flowBizContext) {
        final List<IActivity> executedList = new ArrayList();
        List<IActivity> beforeList = ActivityRegister.buildActivityInstance(this.beforeTrans);
        final List<IActivity> transList = ActivityRegister.buildActivityInstance(this.trans);
        List<IActivity> afterList = ActivityRegister.buildActivityInstance(this.afterTrans);

        try {
            this.executeActivity(beforeList, flowBizContext, executedList, false);
            TransactionTemplate transactionTemplate = null;
            if (this.transEnable) {
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
                        FlowTemplate.this.executeActivity(transList, flowBizContext, executedList, false);
                    }
                });
            } else {
                this.executeActivity(transList, flowBizContext, executedList, false);
            }

            this.executeActivity(afterList, flowBizContext, executedList, true);
        } catch (Exception var9) {
            this.rollbackActivity(executedList, flowBizContext);
            throw var9;
        }
    }

    private void executeActivity(List<IActivity> activities, FlowBizContext flowBizContext, List<IActivity> executedList, boolean catchExp) {
        if (!CollectionUtils.isEmpty(activities)) {
            activities.forEach((iActivity) -> {
                executedList.add(iActivity);

                try {
                    iActivity.execute(flowBizContext);
                } catch (Exception var5) {
                    if (!catchExp) {
                        throw var5;
                    }

                    log.error("执行流程节点异常, activity = {}", iActivity.getClass().getName(), var5);
                }

            });
        }
    }

    private void rollbackActivity(List<IActivity> rollbackList, FlowBizContext flowBizContext) {
        if (!CollectionUtils.isEmpty(rollbackList)) {
            rollbackList.forEach((iActivity) -> {
                try {
                    iActivity.rollback(flowBizContext);
                } catch (Exception var3) {
                    log.error("执行acitivty滚回失败", var3);
                }

            });
        }
    }
}
