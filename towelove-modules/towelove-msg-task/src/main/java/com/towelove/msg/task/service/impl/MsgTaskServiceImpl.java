package com.towelove.msg.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.Query;
import com.towelove.common.core.domain.PageResult;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.domain.vo.MsgTaskCreateReqVO;
import com.towelove.msg.task.domain.vo.MsgTaskPageReqVO;
import com.towelove.msg.task.domain.vo.MsgTaskSimpleRespVO;
import com.towelove.msg.task.domain.vo.MsgTaskUpdateReqVO;
import com.towelove.msg.task.mapper.MsgTaskMapper;
import com.towelove.msg.task.service.MsgTaskService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        //获得当前时间
        //需要查询获得十分钟内的任务数据
//        QueryWrapper<MsgTask> msgTaskQueryWrapper = new QueryWrapper<>();
//        msgTaskQueryWrapper.between(MsgTask::getSendTime, localDateTime, localDateTime.plusMinutes(10));
        List<MsgTask> msgTasks = msgTaskMapper.selectList(new QueryWrapper<>());
        return msgTasks;
    }
}
