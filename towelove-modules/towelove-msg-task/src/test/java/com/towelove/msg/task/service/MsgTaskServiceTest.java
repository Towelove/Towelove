package com.towelove.msg.task.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.mapper.MsgTaskMapper;
import com.xxl.job.core.context.XxlJobHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/12 19:29
 * MsgTaskServiceTest类
 */
@SpringBootTest
public class MsgTaskServiceTest {
    public static void main(String[] args) {
        //MsgTask msgTask = MsgTask.builder().content("!@3123")
        //        .id(1L)
        //        .receiveAccount("460219753@qq.com")
        //        .accountId(1L)
        //        .nickname("zjb").build();
        //MsgTaskRespVO convert = MsgTaskConvert.INSTANCE.convert(msgTask);
        //System.out.println(convert);
        LocalDateTime localTime = LocalDateTime.now();
        System.out.println(localTime);
        System.out.println(localTime.plusMinutes(10));
    }

    @Autowired
    private MsgTaskMapper msgTaskMapper;

    @Test
    public void test() {
        List<MsgTask> msgTasks = msgTaskMapper.selectList();
        LocalDateTime localDateTime = LocalDateTime.now();
        Time start = new Time(localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
        Time end = new Time(localDateTime.getHour(), localDateTime.getMinute() + 10, localDateTime.getSecond());
        List<MsgTask> msgTaskList = msgTaskMapper
                .selectList(new QueryWrapper<MsgTask>()
                        .between("send_time", start,
                                end));
        System.out.println(msgTaskList);
    }
    @Test
    public void test1(){
        //获取总的分片数量
        int total = XxlJobHelper.getShardTotal();
        //获取当前机器的分片索引
        int index = XxlJobHelper.getShardIndex();
        //获得当前时间
        //需要查询获得十分钟内的任务数据
//        QueryWrapper<MsgTask> msgTaskQueryWrapper = new QueryWrapper<>();
//        msgTaskQueryWrapper.between(MsgTask::getSendTime, localDateTime, localDateTime.plusMinutes(10));
        LocalDateTime localDateTime = LocalDateTime.now();
        Time start = new Time(localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
        Time end = new Time(localDateTime.getHour(), localDateTime.getMinute() + 10, localDateTime.getSecond());

        //List<MsgTask> msgTaskList = msgTaskMapper
        //        .selectList(new QueryWrapper<MsgTask>()
        //                .between("send_time", start,
        //                        end));

        List<MsgTask> msgTaskList = msgTaskMapper
                .selectAfterTenMinJob(start, end, total, index);
        System.out.println(msgTaskList);
    }
}
