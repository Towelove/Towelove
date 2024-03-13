package blossom.project.towelove.framework.flower.model.service;

import blossom.project.towelove.framework.flower.model.FlowBizContext;

/**
 * @author: ZhangBlossom
 * @date: 2024/3/12 18:28
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * 定义一个流水线服务接口，
 * 流水线服务接口负责执行流水线任务
 */
public interface FlowSerivce {

    /**
     * 执行流水线任务
     * @param var1
     */
    void execute(FlowBizContext var1);

    /**
     * 回滚流水线任务
     * @param flowBizContext
     */
    default void rollback(FlowBizContext flowBizContext) {
    }
}
