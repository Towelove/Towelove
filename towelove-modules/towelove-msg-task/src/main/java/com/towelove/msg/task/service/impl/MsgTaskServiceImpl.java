package com.towelove.msg.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.towelove.common.core.constant.MsgTaskConstants;
import com.towelove.common.core.domain.PageResult;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.domain.vo.MsgTaskCreateReqVO;
import com.towelove.msg.task.domain.vo.MsgTaskPageReqVO;
import com.towelove.msg.task.domain.vo.MsgTaskSimpleRespVO;
import com.towelove.msg.task.domain.vo.MsgTaskUpdateReqVO;
import com.towelove.msg.task.mapper.MsgTaskMapper;
import com.towelove.msg.task.mq.producer.MsgTaskProducer;
import com.towelove.msg.task.service.MsgTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    @Autowired
    private MsgTaskProducer msgTaskProducer;
    private boolean sendToMQOrNot(Date sendTime) {
        Date now = new Date();
        //要发送的时间在当前时间之后 并且是在当前发送的时间间隔
        //那么就吧这个任务需要放入到mq中
        Long diff = sendTime.getTime() - now.getTime();
        if (diff > 0L && diff < MsgTaskConstants.SEND_TIME_DIFF) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Long createMsgTask(MsgTaskCreateReqVO createReqVO) {
        if (Objects.isNull(createReqVO)) {
            throw new RuntimeException("用户传来的对象为空");
        }
        MsgTask msgTask = new MsgTask();
        BeanUtils.copyProperties(createReqVO, msgTask);
        try {
            int Isinsert = msgTaskMapper.insert(msgTask);
        } catch (Exception e) {
            throw new RuntimeException("新增任务失败");
        }
        if (sendToMQOrNot(createReqVO.getSendTime())) {
            //添加不发生异常 则发送创建消息
            //还需要判断消息的时间是否需要发送
            msgTaskProducer.sendMsgCreateEvent(createReqVO);
        }
        return msgTask.getId();
    }

    @Override
    public Boolean updateMsgTask(MsgTaskUpdateReqVO updateReqVO) {
        if (Objects.isNull(updateReqVO)) {
            throw new RuntimeException("前端传来的对象为空");
        }
        MsgTask msgTask = new MsgTask();
        BeanUtils.copyProperties(updateReqVO, msgTask);
        int isUpdate = msgTaskMapper.updateById(msgTask);
        if (isUpdate == 0) {
            throw new RuntimeException("修改任务失败");
        }
        //发送消息到mq
        if (sendToMQOrNot(updateReqVO.getSendTime())) {
            msgTaskProducer.sendMsgUpdateEvent(updateReqVO);
        }
        return isUpdate > 0;
    }



    @Override
    public Boolean deleteMsgTask(Long id) {
        if (null == id) {
            throw new RuntimeException("id为空...");
        }
        int i = msgTaskMapper.deleteById(id);
        //由于是删除 所以直接去判断mq里面有没有这个id对应的消息即可
        //如果有就直接取出这个任务 没有就无事发生
        msgTaskProducer.sendMsgDeleteEvent(id);
        return i > 0;
    }

    @Override
    public MsgTask getMsgTask(Long id) {
        if (null == id) {
            throw new RuntimeException("id为空...");
        }
        MsgTask msgTask = msgTaskMapper.selectById(id);
        if (Objects.isNull(msgTask)) {
            throw new RuntimeException("获得消息任务失败");
        }
        return msgTask;
    }

    @Override
    public PageResult<MsgTask> getMsgTaskPage(MsgTaskPageReqVO pageReqVO) {
        if (Objects.isNull(pageReqVO)) {
            throw new RuntimeException("查询数据失败");
        }
        IPage page = new Page(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        QueryWrapper<MsgTask> msgTaskQueryWrapper = new QueryWrapper<>();
        //带分页的条件查询
        page = msgTaskMapper.selectPage(page, msgTaskQueryWrapper
                .eq(Strings.isNotBlank(pageReqVO.getContent()), "content", pageReqVO.getContent())
                .eq(Strings.isNotBlank(pageReqVO.getTitle()), "title", pageReqVO.getTitle())
                .eq(Strings.isNotBlank(pageReqVO.getNickname()), "nickname", pageReqVO.getNickname())
                .eq(null != pageReqVO.getSendTime(), "send_time", pageReqVO.getSendTime())
        );
        PageResult<MsgTask> msgTaskPageResult = new PageResult<>();
        msgTaskPageResult.setList(page.getRecords());
        msgTaskPageResult.setTotal(page.getTotal());
        if (Objects.isNull(msgTaskPageResult)) {
            throw new RuntimeException("查询对象失败");
        }
        return msgTaskPageResult;
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

    @Override
    public List<MsgTaskSimpleRespVO> getSimpleMailAccountList() {
        List<MsgTask> list = this.getMsgTaskList();
        List<MsgTaskSimpleRespVO> msgTaskSimpleRespVOList = list.stream().map(
                msgTask -> {
                    MsgTaskSimpleRespVO msgTaskSimpleRespVO = new MsgTaskSimpleRespVO();
                    BeanUtils.copyProperties(msgTask, msgTaskSimpleRespVO);
                    return msgTaskSimpleRespVO;
                }).collect(Collectors.toList());
        return msgTaskSimpleRespVOList;
    }
}
