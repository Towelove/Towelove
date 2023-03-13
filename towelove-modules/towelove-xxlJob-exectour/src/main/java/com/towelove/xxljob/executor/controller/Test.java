package com.towelove.xxljob.executor.controller;

import com.towelove.xxljob.executor.mq.producer.SysMessageProducer;
import com.towelove.xxljob.executor.mq.producer.TaskMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 张锦标
 * @date: 2023/3/13 13:40
 * Test类
 */
@RestController
public class Test {
    @Autowired
    private SysMessageProducer sysMessageProducer;
    @Autowired
    private TaskMessageProducer taskMessageProducer;
    @GetMapping("/sys")
    public void syssend(){
        sysMessageProducer.sendMailSendMessage(
                1L,"460219753@qq.com",1L,
                "460219753@qq.com","helo","123"
        );
    }
}
