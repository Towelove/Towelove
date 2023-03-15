package com.towelove.msg.task.service;

import com.towelove.msg.task.domain.MsgTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author 季台星
 * @Date 2023 03 15 18 48
 */
@SpringBootTest
public class DateTest {
    @Autowired
    private MsgTaskService msgTaskService;
    @Test
    public void test(){
        List<MsgTask> msgTaskList = msgTaskService.getMsgTaskList();
        System.out.println(msgTaskList);
    }
}
