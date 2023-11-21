package blossom.project.towelove.msg.service.Impl;

import blossom.project.towelove.common.constant.SecurityConstants;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.client.LoveLogClient;
import blossom.project.towelove.msg.controller.MsgTaskController;
import blossom.project.towelove.msg.convert.MsgTaskConvert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import blossom.project.towelove.msg.entity.MsgTask;
import blossom.project.towelove.msg.mapper.MsgTaskMapper;
import blossom.project.towelove.msg.service.MsgTaskService;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
import blossom.project.towelove.common.request.msg.MsgTaskCreateRequest;
import blossom.project.towelove.common.request.msg.MsgTaskPageRequest;
import blossom.project.towelove.common.request.msg.MsgTaskUpdateRequest;

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
    public MsgTaskResponse getMsgTaskById(Long MsgTaskId) {
        return null;
    }

    @Override
    public PageResponse<MsgTaskResponse> pageQueryMsgTask(MsgTaskPageRequest requestParam) {
        return null;
    }

    @Override
    public MsgTaskResponse updateMsgTask(MsgTaskUpdateRequest updateRequest) {
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
    public Result createMsgTask(MsgTaskCreateRequest createRequest) {
        MsgTask msgTask = MsgTaskConvert.INSTANCE.convert(createRequest);
        int success = msgTaskMapper.insert(msgTask);
        if (success > 0) {
            return Result.ok(msgTask);
        } else {
            return Result.fail(MDC.get(SecurityConstants.REQUEST_ID));
        }
    }
}

