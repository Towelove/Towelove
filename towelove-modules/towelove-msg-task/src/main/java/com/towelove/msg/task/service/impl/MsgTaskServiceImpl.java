package com.towelove.msg.task.service.impl;

import cn.hutool.core.date.DateTime;
import com.alibaba.nacos.shaded.com.google.protobuf.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.Query;
import com.towelove.common.core.constant.MessageConstant;
import com.towelove.common.core.constant.MsgTaskConstants;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.domain.R;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.domain.vo.MsgTaskCreateReqVO;
import com.towelove.msg.task.domain.vo.MsgTaskPageReqVO;
import com.towelove.msg.task.domain.vo.MsgTaskSimpleRespVO;
import com.towelove.msg.task.domain.vo.MsgTaskUpdateReqVO;
import com.towelove.msg.task.mapper.MsgTaskMapper;
import com.towelove.msg.task.mq.producer.MsgTaskProducer;
import com.towelove.msg.task.service.MsgTaskService;
import jdk.nashorn.internal.ir.IfNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private boolean needToChangeMap(Date taskDate) {
        long time = taskDate.getTime();
        Date date = new Date();
        long now = date.getTime();
        //要发送的时间和当前时间差
        long diff = time - now;
        //如果当前时间是下一个时间周期
        //就允许发送
        if (diff>0 &&
                diff < MsgTaskConstants.SEND_TIME_DIFF) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 插件新增消息并且判断是否需要发送到mq
     *
     * @param createReqVO 消息任务消息
     * @return 新增消息的id
     */
    @Override
    public Long createMsgTask(MsgTaskCreateReqVO createReqVO) {
        if (Objects.isNull(createReqVO)) {
            throw new RuntimeException("用户传来的对象为空");
        }
        MsgTask msgTask = new MsgTask();
        BeanUtils.copyProperties(createReqVO, msgTask);
        try {
            int isInsert = msgTaskMapper.insert(msgTask);
            if (isInsert > 0 && needToChangeMap(
                    createReqVO.getSendTime())) {
                msgTaskProducer.sendMsgCreateEvent(msgTask);
            }
        } catch (Exception e) {
            throw new RuntimeException("新增任务失败");
        }
        return msgTask.getUserId();
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
        }else if (needToChangeMap(updateReqVO.getSendTime())){
            msgTaskProducer.sendMsgUpdateEvent(msgTask);
        }
        return isUpdate > 0;
    }

    @Override
    public Boolean deleteMsgTask(Long id) {
        if (null == id) {
            throw new RuntimeException("id为空...");
        }
        int i = msgTaskMapper.deleteById(id);
        if (i>0){
            msgTaskProducer.sendMsgDeleteEvent(id);
        }
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
        DateTime dateTime1 = DateTime.now();
        DateTime dateTime2 = DateTime.now();
        dateTime1.setMinutes(dateTime1.getMinutes() - 5);
        dateTime2.setMinutes(dateTime2.getMinutes() + 10);
        List<MsgTask> msgTasks = msgTaskMapper
                .selectList(new QueryWrapper<MsgTask>()
                        .between("send_time", dateTime1.toString(),
                                dateTime2.toString()));
        //List<MsgTask> msgTasks = msgTaskMapper.
        //        selectAfterTenMinJob(dateTime1, dateTime2);
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
