package com.towelove.msg.task.service;

import com.towelove.msg.task.convert.MsgTaskConvert;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.domain.vo.MsgTaskRespVO;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/3/12 19:29
 * MsgTaskServiceTest类
 */
@SpringBootTest
public class MsgTaskServiceTest {
    public static void main(String[] args) {
        MsgTask msgTask = MsgTask.builder().content("!@3123")
                .id(1L).deleted(false)
                .receiveAccount("460219753@qq.com")
                .sendTime(new Date())
                .accountId(1L)
                .nickname("zjb").build();
        MsgTaskRespVO convert = MsgTaskConvert.INSTANCE.convert(msgTask);
        System.out.println(convert);
    }
}
