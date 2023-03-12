package com.towelove.msg.task.service.impl;

import com.towelove.common.core.domain.PageResult;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.domain.vo.MsgTaskCreateReqVO;
import com.towelove.msg.task.domain.vo.MsgTaskPageReqVO;
import com.towelove.msg.task.domain.vo.MsgTaskUpdateReqVO;
import com.towelove.msg.task.mapper.MsgTaskMapper;
import com.towelove.msg.task.service.MsgTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:47
 * MsgTaskServiceImpl类
 */
@Service
@Slf4j
@Validated
public class MsgTaskServiceImpl implements MsgTaskService {
    @Autowired
    private MsgTaskMapper msgTaskMapper;
    @Override
    public Long createMsgTask(MsgTaskCreateReqVO createReqVO) {
        return null;
    }

    @Override
    public Boolean updateMsgTask(MsgTaskUpdateReqVO updateReqVO) {
        return null;
    }

    @Override
    public Boolean deleteMsgTask(Long id) {
        return null;
    }

    @Override
    public MsgTask getMsgTask(Long id) {
        return null;
    }

    @Override
    public PageResult<MsgTask> getMsgTaskPage(MsgTaskPageReqVO pageReqVO) {
        return null;
    }

    @Override
    public List<MsgTask> getMsgTaskList() {
        return null;
    }
}
