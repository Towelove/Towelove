package com.towelove.spring.cloud.mq.controller;


import com.towelove.common.core.constant.MessageConstant;
import com.towelove.common.core.domain.R;
import com.towelove.spring.cloud.mq.producer.SysMessageProducer;
import com.towelove.spring.cloud.mq.producer.TaskMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 张锦标
 * @date: 2023/3/2 20:23
 * SpringCloudStreamApplication类
 */
@RestController
public class RocketMqSendMsgController {
    @Autowired
    private SysMessageProducer sysMessageProducer;
    @Autowired
    private TaskMessageProducer taskMessageProducer;
    @Autowired
    private StreamBridge streamBridge;
    @GetMapping("/sys")
    public R<Boolean> sendSysMsg(){
        streamBridge.send(MessageConstant.SYS_MESSAGE_OUTPUT,"123");
        return R.ok(Boolean.TRUE);
    }
    @GetMapping("/task")
    public R<Boolean> sendTaskMsg(){
        streamBridge.send(MessageConstant.TASK_MESSAGE_OUTPUT,"213456");
        return R.ok(Boolean.TRUE);
    }


}