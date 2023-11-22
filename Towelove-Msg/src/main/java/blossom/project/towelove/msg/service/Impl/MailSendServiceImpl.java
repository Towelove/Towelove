package blossom.project.towelove.msg.service.Impl;

import blossom.project.towelove.common.utils.StringUtils;
import blossom.project.towelove.msg.entity.CompletedMailMsgTask;
import blossom.project.towelove.msg.service.MailSendService;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/22 17:01
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MailSendServiceImpl类
 */
@Service
@Slf4j
public class MailSendServiceImpl implements MailSendService {

    @Override
    public String sendCompletedMailMsg(CompletedMailMsgTask mail) {
        log.info("接收到定时任务消息，并且准备发送给MQ：{}",mail);
        String msgId = sendMailMsg(mail);
        return msgId;
    }


    public String sendMailMsg(CompletedMailMsgTask mailMsg) {
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
            //TODO 这里可以配合线程池
            throw new RuntimeException(e);
        }finally {
            return msgId;
        }
    }
}
