package blossom.project.towelove.msg.service;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.msg.entity.MsgTask;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
import blossom.project.towelove.common.request.msg.MsgTaskCreateRequest;
import blossom.project.towelove.common.request.msg.MsgTaskPageRequest;
import blossom.project.towelove.common.request.msg.MsgTaskUpdateRequest;

import java.util.List;

/**
 * (MsgTask) 表服务接口
 *
 * @author 张锦标
 * @since 2023-11-21 19:33:06
 */
public interface MsgTaskService extends IService<MsgTask> {
    
    MsgTaskResponse getMsgTaskById(Long MsgTaskId);

    PageResponse<MsgTaskResponse> pageQueryMsgTask(MsgTaskPageRequest requestParam);

    MsgTaskResponse updateMsgTask(MsgTaskUpdateRequest updateRequest);

    Boolean deleteMsgTaskById(Long MsgTaskId);

    Boolean batchDeleteMsgTask(List<Long> ids);

    MsgTaskResponse createMsgTask(MsgTaskCreateRequest createRequest);

    /**
     * 获取所有的消息
     * @return 所有的查询消息
     */
    List<MsgTask> getMsgTaskList();
}

