package com.towelove.msg.task.mq.consumer.mail;

import com.towelove.common.core.constant.MsgTaskConstants;
import com.towelove.msg.task.config.TaskMapUtil;
import com.towelove.msg.task.domain.MailMsg;
import com.towelove.msg.task.domain.vo.MsgTaskCreateReqVO;
import com.towelove.msg.task.domain.vo.MsgTaskUpdateReqVO;
import com.towelove.msg.task.executor.SimpleXxxJob;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
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
    public Consumer<MailMsg> msgUpdate(){
        return mailMsg->{
            log.info("接收到消息修改事件，修改后内容为：{}",mailMsg);
            //TODO 业务操作
            TaskMapUtil.getTaskMap().put(MsgTaskConstants.MSG_PREFIX+mailMsg.getId(),
                    mailMsg);
        };
    }
    @Bean
    public Consumer<MailMsg> msgCreate(){
        return mailMsg->{
            log.info("接收到消息新增事件，新增的内容为：{}",mailMsg);
            //添加这个map中的消息
            ConcurrentHashMap<String, MailMsg> map = TaskMapUtil.getTaskMap();
            System.out.println(map);
            map.put(MsgTaskConstants.MSG_PREFIX+mailMsg.getId(),
                    mailMsg);
        };
    }
    @Bean
    public Consumer<Long> msgDelete(){
        return id->{
            log.info("接收到消息删除事件，删除的id为：{}",id);
            //删除这个map中的消息
            TaskMapUtil.getTaskMap().remove(MsgTaskConstants.MSG_PREFIX+id);
        };
    }
}
