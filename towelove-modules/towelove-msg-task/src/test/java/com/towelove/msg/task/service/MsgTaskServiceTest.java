package com.towelove.msg.task.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.mapper.MsgTaskMapper;
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
}
