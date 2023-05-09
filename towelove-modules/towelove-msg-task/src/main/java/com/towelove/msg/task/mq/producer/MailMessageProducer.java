package com.towelove.msg.task.mq.producer;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.towelove.common.core.constant.MessageConstant;
import com.towelove.common.core.domain.MailSendMessage;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.common.mq.core.bus.AbstractBusProducer;
import com.towelove.msg.task.domain.MailMsg;
import com.towelove.system.api.RemoteSendLog;
import com.towelove.system.api.model.SendLogDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: 张锦标
 * @date: 2023/3/13 12:15
 * TaskMessageProducer类
 */
@Component
@Slf4j
public class MailMessageProducer extends AbstractBusProducer {
    @Resource
    private StreamBridge streamBridge;
    @Autowired
    @Qualifier("logThreadPool")
    private ThreadPoolExecutor LOG_THREAD_POOL;

    @Autowired
    private RemoteSendLog remoteSendLog;
    /**
     * 发送 {@link MailMsg} 消息给指定用户
     *
     */
    public void sendMailMessage(MailMsg mail) {
        //TODO 这里需要配置延迟线程池 来完成消息延迟发送给rocketmq
        log.info("接收到定时任务消息，并且准备发送给MQ：{}",mail);
        //streamBridge.send(MessageConstant.TASK_MESSAGE_OUTPUT,
        //        mail);
        sendMailMsg(mail);
        log.info("消息发送给MQ成功。。。");
    }

    public void sendMailMsg(MailMsg mailMsg) {
        log.info("接收到任务消息消息: {}", mailMsg);
        //TODO 真正的发送消息并且记录日志

        String from = StringUtils.isNotEmpty(mailMsg.getNickname()) ?
                mailMsg.getNickname() + " <" + mailMsg.getMail() + ">" : mailMsg.getMail();
        //配置MailAccount对象 hutool提供的
        MailAccount mailAccount =
                new MailAccount().setFrom(from).setAuth(true)
                        .setUser(mailMsg.getMail())
                        .setPass(mailMsg.getPassword()).setHost(mailMsg.getHost())
                        .setPort(mailMsg.getPort()).setSslEnable(mailMsg.getSslEnable());
        //发送邮件 msgIG为邮件id
        String msgId = null;
        try {
            //TODO 这里需要完成消息的延时发送
            msgId = MailUtil.send(mailAccount, mailMsg.getReceiveAccount(),
                    mailMsg.getTitle(), mailMsg.getContent(),
                    false, null);
        } catch (Exception e) {
            String finalMsgId = msgId;
            //远程调用日志记录
            //这里可以配合线程池
            LOG_THREAD_POOL.execute(() -> {
                remoteSendLog.createSendLog(
                        new SendLogDo().setSendEmail(mailMsg.getMail())
                                .setReceiveEmail(mailMsg.getReceiveAccount())
                                .setSendStatus(StringUtils.isNotEmpty(finalMsgId) ? 1 : 0)
                                .setSendError(e.getMessage()));
            });
            throw new RuntimeException(e);
        }
    }
}
