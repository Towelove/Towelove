package com.towelove.msg.task.mq.producer;

import com.towelove.common.core.constant.MessageConstant;
import com.towelove.common.mq.core.bus.AbstractBusProducer;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.domain.vo.MsgTaskCreateReqVO;
import com.towelove.msg.task.domain.vo.MsgTaskUpdateReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: 张锦标
 * @date: 2023/3/14 9:27
 * MsgTaskProducer类
 */
@Slf4j
@Component
public class MsgTaskProducer extends AbstractBusProducer {
    @Resource
    private StreamBridge streamBridge;

    public void sendMsgUpdateEvent(MsgTaskUpdateReqVO updateReqVO){
        log.info("接收到消息修改事件，修改后内容为：{}",updateReqVO);
        streamBridge.send(MessageConstant.MESSAGE_UPDATE_OUTPUT,updateReqVO);
    }
    public void sendMsgCreateEvent(MsgTaskCreateReqVO createReqVO){
        log.info("接收到消息创建事件，新增的消息内容为：{}",createReqVO);
        streamBridge.send(MessageConstant.MESSAGE_CREATE_OUTPUT,createReqVO);
    }
    public void sendMsgDeleteEvent(Long id){
        log.info("接收到消息删除事件，删除的消息id为：{}",id);
        streamBridge.send(MessageConstant.MESSAGE_DELETE_OUTPUT,id);
    }
}
