package blossom.project.towelove.msg.service;


import blossom.project.towelove.common.page.PageResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.msg.entity.MsgTask;
import blossom.project.towelove.msg.dto.MsgTaskRespDTO;
import blossom.project.towelove.msg.req.MsgTaskCreateRequest;
import blossom.project.towelove.msg.req.MsgTaskPageRequest;
import blossom.project.towelove.msg.req.MsgTaskUpdateRequest;

import java.util.List;

/**
 * (MsgTask) 表服务接口
 *
 * @author 张锦标
 * @since 2023-11-21 19:33:06
 */
public interface MsgTaskService extends IService<MsgTask> {
    
    MsgTaskRespDTO getMsgTaskById(Long MsgTaskId);

    PageResponse<MsgTaskRespDTO> pageQueryMsgTask(MsgTaskPageRequest requestParam);

    MsgTaskRespDTO updateMsgTask(MsgTaskUpdateRequest updateRequest);

    Boolean deleteMsgTaskById(Long MsgTaskId);

    Boolean batchDeleteMsgTask(List<Long> ids);

    MsgTaskRespDTO createMsgTask(MsgTaskCreateRequest createRequest);
}

