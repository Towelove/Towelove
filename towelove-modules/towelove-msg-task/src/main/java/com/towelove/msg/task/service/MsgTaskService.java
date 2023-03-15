package com.towelove.msg.task.service;

import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.domain.R;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.domain.vo.MsgTaskCreateReqVO;
import com.towelove.msg.task.domain.vo.MsgTaskPageReqVO;
import com.towelove.msg.task.domain.vo.MsgTaskSimpleRespVO;
import com.towelove.msg.task.domain.vo.MsgTaskUpdateReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:47
 * MsgTaskService接口
 */
public interface MsgTaskService {
    /**
     * 创建消息任务消息
     * @param createReqVO 消息任务消息
     * @return 返回创建的id
     */
    Long createMsgTask(@Valid MsgTaskCreateReqVO createReqVO);

    /**
     * 修改消息任务消息
     * @param updateReqVO 修改后的消息任务消息
     * @return 返回是否成功
     */
    Boolean updateMsgTask(@Valid MsgTaskUpdateReqVO updateReqVO);

    /**
     * 删除消息任务消息
     * @param id 要删除的消息
     * @return 返回是否成功
     */
    Boolean deleteMsgTask(Long id);

    /**
     * 根据id获取消息任务信息
     * @param id 要获取的消息任务的id
     * @return 返回消息任务消息
     */
    MsgTask getMsgTask(Long id);

    /**
     * 分页查询消息任务消息
     * @param pageReqVO 查询条件
     * @return 返回分页结果
     */
    PageResult<MsgTask> getMsgTaskPage(MsgTaskPageReqVO pageReqVO);

    /**
     * 获取所有的消息
     * @return 所有的查询消息
     */
    List<MsgTask> getMsgTaskList();

    List<MsgTaskSimpleRespVO> getSimpleMailAccountList();
}
