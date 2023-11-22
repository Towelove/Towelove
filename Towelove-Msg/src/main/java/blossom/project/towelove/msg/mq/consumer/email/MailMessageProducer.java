package blossom.project.towelove.msg.mq.consumer.email;

import blossom.project.towelove.common.utils.StringUtils;
import blossom.project.towelove.msg.entity.CompletedMailMsgTask;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailMessageProducer{
    /**
     * 发送 {@link CompletedMailMsgTask} 消息给指定用户
     *
     */
    public void sendMailMessage(CompletedMailMsgTask mail) {
        //TODO 这里需要配置延迟线程池 来完成消息延迟发送给rocketmq
        log.info("接收到定时任务消息，并且准备发送给MQ：{}",mail);
        sendMailMsg(mail);
        log.info("消息发送给MQ成功。。。");
    }

    public void sendMailMsg(CompletedMailMsgTask completedMsgTaskMail) {
        log.info("接收到任务消息消息: {}", completedMsgTaskMail);
        //TODO 真正的发送消息并且记录日志

        String from = StringUtils.isNotEmpty(completedMsgTaskMail.getNickname()) ?
                completedMsgTaskMail.getNickname() + " <" + completedMsgTaskMail.getMail() + ">" : completedMsgTaskMail.getMail();
        //配置MailAccount对象 hutool提供的
        MailAccount mailAccount =
                new MailAccount().setFrom(from).setAuth(true)
                        .setUser(completedMsgTaskMail.getMail())
                        .setPass(completedMsgTaskMail.getPassword()).setHost(completedMsgTaskMail.getHost())
                        .setPort(completedMsgTaskMail.getPort()).setSslEnable(completedMsgTaskMail.getSslEnable());
        //发送邮件 msgIG为邮件id
        String msgId = null;
        try {
            //TODO 这里需要完成消息的延时发送
            msgId = MailUtil.send(mailAccount, completedMsgTaskMail.getReceiveAccount(),
                    completedMsgTaskMail.getTitle(), completedMsgTaskMail.getContent(),
                    false, null);
        } catch (Exception e) {
            String finalMsgId = msgId;
            //远程调用日志记录
            //这里可以配合线程池
            throw new RuntimeException(e);
        }
    }
}
