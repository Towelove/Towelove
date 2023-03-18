package com.towelove.msg.task.mq.producer;

import cn.hutool.extra.mail.MailException;
import com.towelove.common.core.constant.MessageConstant;

import com.towelove.common.core.domain.R;
import com.towelove.common.core.utils.bean.BeanUtils;
import com.towelove.common.mq.core.bus.AbstractBusProducer;
import com.towelove.msg.task.domain.MailMsg;
import com.towelove.msg.task.domain.MsgTask;

import com.towelove.system.api.RemoteSysMailAccountService;
import com.towelove.system.api.model.MailAccountRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

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

    @Autowired
    private RemoteSysMailAccountService remoteSysMailAccountService;

    public void sendMsgUpdateEvent(MsgTask msgTask) {
        log.info("接收到消息修改事件，修改后内容为：{}", msgTask);
        MailMsg msg = new MailMsg();
        BeanUtils.copyProperties(msgTask, msg);
        MailAccountRespVO mailAccount = remoteSysMailAccountService
                .getMailAccount(msgTask.getAccountId()).getData();
        if (Objects.nonNull(mailAccount)) {
            BeanUtils.copyProperties(mailAccount, msg);
            //将所有的任务放入到map中暂存
            streamBridge.send(MessageConstant.MESSAGE_UPDATE_OUTPUT,
                    msg);
            System.out.println("修改任务消息事件发送给MQ成功！");
        } else {
            throw new MailException("邮箱账户为空，出现异常！！！");
        }
    }

    public void sendMsgCreateEvent(MsgTask msgTask) {
        log.info("接收到消息创建事件，新增的消息内容为：{}", msgTask);
        MailMsg msg = new MailMsg();
        BeanUtils.copyProperties(msgTask, msg);
        MailAccountRespVO mailAccount = remoteSysMailAccountService
                .getMailAccount(msgTask.getAccountId()).getData();
        if (Objects.nonNull(mailAccount)) {
            BeanUtils.copyProperties(mailAccount, msg);
            //将所有的任务放入到map中暂存
            streamBridge.send(MessageConstant.MESSAGE_CREATE_OUTPUT,
                    msg);
            System.out.println("插件任务消息事件发送给MQ成功！");
        } else {
            throw new MailException("邮箱账户为空，出现异常！！！");
        }
    }

    public void sendMsgDeleteEvent(Long id) {
        log.info("接收到消息删除事件，删除的消息id为：{}", id);
        streamBridge.send(MessageConstant.MESSAGE_DELETE_OUTPUT, id);
    }
}
