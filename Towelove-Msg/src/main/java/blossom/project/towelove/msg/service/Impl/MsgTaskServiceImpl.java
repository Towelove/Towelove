package blossom.project.towelove.msg.service.Impl;

import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.framework.log.client.LoveLogClient;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import blossom.project.towelove.msg.entity.MsgTask;
import blossom.project.towelove.msg.mapper.MsgTaskMapper;
import blossom.project.towelove.msg.service.MsgTaskService;
import blossom.project.towelove.msg.dto.MsgTaskRespDTO;
import blossom.project.towelove.msg.req.MsgTaskCreateRequest;
import blossom.project.towelove.msg.req.MsgTaskPageRequest;
import blossom.project.towelove.msg.req.MsgTaskUpdateRequest;

import java.util.List;


/**
 * (MsgTask) 表服务实现类
 *
 * @author 张锦标
 * @since 2023-11-21 19:33:06
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MsgTaskServiceImpl extends ServiceImpl<MsgTaskMapper, MsgTask> implements MsgTaskService {
 
    private final MsgTaskMapper msgTaskMapper;

    private final LoveLogClient logClient;

    @Override
    public MsgTaskRespDTO getMsgTaskById(Long MsgTaskId) {
        return null;
    }

    @Override
    public PageResponse<MsgTaskRespDTO> pageQueryMsgTask(MsgTaskPageRequest requestParam) {
        return null;
    }

    @Override
    public MsgTaskRespDTO updateMsgTask(MsgTaskUpdateRequest updateRequest) {
        return null;
    }

    @Override
    public Boolean deleteMsgTaskById(Long MsgTaskId) {
        return null;
    }

    @Override
    public Boolean batchDeleteMsgTask(List<Long> ids) {
        return null;
    }

    @Override
    public MsgTaskRespDTO createMsgTask(MsgTaskCreateRequest createRequest) {
        return null;
    }
}

