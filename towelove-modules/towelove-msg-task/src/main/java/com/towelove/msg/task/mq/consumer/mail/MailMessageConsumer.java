package com.towelove.msg.task.mq.consumer.mail;



import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.towelove.common.core.domain.MailSendMessage;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.msg.task.domain.MailMsg;
import com.towelove.system.api.RemoteSendLog;
import com.towelove.system.api.model.MailAccountRespVO;
import com.towelove.system.api.model.SendLogDo;
import jdk.internal.joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

/**
 * @author: 张锦标
 * @date: 2023/3/13 22:23
 * TaskMessageConsumer类
 */
@Component
@Slf4j
public class MailMessageConsumer implements Consumer<MailMsg> {

    @Autowired
    @Qualifier("logThreadPool")
    private ThreadPoolExecutor LOG_THREAD_POOL;

    @Autowired
    private RemoteSendLog remoteSendLog;

    String msgId = null;



    //TODO 还需要编写一个对应的日志记录类
    //TODO 或者使用远程调用去记录日志
    //这个日志记录应该是异步的 不然很浪费性能
    //也就是我可以新开一个线程去记录日志
    //可以使用当初实习的时候的异步日志记录方法

    @Override
    public void accept(MailMsg mailMsg) {
        log.info("接收到任务消息消息: {}",mailMsg);
        //TODO 真正的发送消息并且记录日志

        String from = StringUtils.isNotEmpty(mailMsg.getNickname()) ?
                mailMsg.getNickname() +" <"+mailMsg.getMail()+">": mailMsg.getMail();
        MailAccount mailAccount = new MailAccount().setFrom(from).setAuth(true)
                .setUser(mailMsg.getUsername()).setPass(mailMsg.getPassword())
                .setHost(mailMsg.getHost()).setPort(mailMsg.getPort())
                .setSslEnable(mailMsg.getSslEnable());
        //发送邮件 msgIG为邮件id
        try {
            msgId = MailUtil.send(mailAccount, mailMsg.getReceiveAccount(),
                    mailMsg.getTitle(), mailMsg.getContent(), false, null);
        } catch (Exception e) {
            LOG_THREAD_POOL.execute(()->{
                //TODO 日志记录 远程日志记录
                remoteSendLog.createSendLog(new SendLogDo().setSendEmail(mailMsg.getMail())
                        .setReceiveEmail(mailMsg.getReceiveAccount())
                        .setSendStatus(StringUtils.isNotEmpty(msgId) ? 1 : 0 )
                        .setSendError(e.getMessage()));
            });
            throw new RuntimeException(e);
        }
        //TODO 远程调用日志记录
        //这里可以配合线程池
        LOG_THREAD_POOL.execute(()->{
            //TODO 日志记录 远程日志记录
            remoteSendLog.createSendLog(new SendLogDo().setSendEmail(mailMsg.getMail())
                    .setReceiveEmail(mailMsg.getReceiveAccount())
                    .setSendStatus(StringUtils.isNotEmpty(msgId) ? 1 : 0 ));
        });
    }
}
