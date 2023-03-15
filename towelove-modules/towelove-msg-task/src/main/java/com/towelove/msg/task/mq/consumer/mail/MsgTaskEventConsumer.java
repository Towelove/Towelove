package com.towelove.msg.task.mq.consumer.mail;

import com.towelove.common.core.constant.MsgTaskConstants;
import com.towelove.msg.task.domain.vo.MsgTaskCreateReqVO;
import com.towelove.msg.task.domain.vo.MsgTaskUpdateReqVO;
import com.towelove.msg.task.executor.SimpleXxxJob;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author: 张锦标
 * @date: 2023/3/15 9:31
 * MsgTaskEventConsumer类
 */
@Component
@Slf4j
public class MsgTaskEventConsumer {
    @Bean
    public Consumer<MsgTaskUpdateReqVO> msgUpdate(){
        return updateReqVO->{
            log.info("接收到消息修改事件，修改后内容为：{}",updateReqVO);
            //TODO 业务操作
        };
    }
    @Bean
    public Consumer<MsgTaskCreateReqVO> msgCreate(){
        return createReqVO->{
            log.info("接收到消息新增事件，新增的内容为：{}",createReqVO);
            //删除这个map中的消息
            //SimpleXxxJob.getMap().remove(MsgTaskConstants.MSG_PREFIX+createReqVO.get);
        };
    }
    @Bean
    public Consumer<Long> msgDelete(){
        return id->{
            log.info("接收到消息删除事件，删除的id为：{}",id);
            //删除这个map中的消息
            SimpleXxxJob.getMap().remove(MsgTaskConstants.MSG_PREFIX+id);
        };
    }
}
