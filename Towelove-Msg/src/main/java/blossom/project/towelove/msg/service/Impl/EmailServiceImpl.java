package blossom.project.towelove.msg.service.Impl;

import blossom.project.towelove.common.constant.RedisKeyConstant;
import blossom.project.towelove.common.utils.CodeGeneratorUtil;
import blossom.project.towelove.common.utils.StringUtils;
import blossom.project.towelove.framework.redis.service.RedisService;
import blossom.project.towelove.msg.entity.CompletedMailMsgTask;
import blossom.project.towelove.msg.entity.OfficialMailInfo;
import blossom.project.towelove.msg.service.EmailService;
import blossom.project.towelove.msg.service.MsgTaskService;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

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
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final RedisService redisService;

    private final OfficialMailInfo officialMailInfo;

    private final MsgTaskService msgTaskService;
    @Override
    public String sendCompletedMailMsg(CompletedMailMsgTask mail) {
        log.info("接收到定时任务消息，并且准备发送给MQ：{}", mail);
        sendMailMsg(mail);
        return "没报错就是发送成功哈哈哈哈";
    }

    @Override
    public String generateValidateCode(String email) {
        String code = CodeGeneratorUtil.generateFourDigitCode();
        sendOfficalEmail(email, RedisKeyConstant.VALIDATE_CODE_SUBJECT, code, false, null);
        return "没报错就是发送成功哈哈哈哈";
    }


    private void sendMailMsg(CompletedMailMsgTask mailMsg) {
        log.info("接收到任务消息消息: {}", mailMsg);
        if (StringUtils.isBlank(mailMsg.getMail())){
            mailMsg.setMail(mailMsg.getUsername());
        }

        String from = StringUtils.isNotEmpty(mailMsg.getNickname()) ?
                mailMsg.getNickname() + " <" + mailMsg.getMail() + ">" : mailMsg.getMail();
        //配置MailAccount对象 hutool提供的
        MailAccount mailAccount =
                new MailAccount().setFrom(from).setAuth(true).setUser(mailMsg.getMail()).setPass(mailMsg.getPassword()).setHost(mailMsg.getHost()).setPort(mailMsg.getPort()).setSslEnable(mailMsg.getSslEnable());
        //发送邮件 msgIG为邮件id
        try {
            MailUtil.send(mailAccount, mailMsg.getReceiveAccount(),
                    mailMsg.getTitle(), mailMsg.getContent(), false,
                    null);
            //删除记录
            msgTaskService.deleteMsgTaskById(mailMsg.getId());
        } catch (Exception e) {
            //远程调用日志记录
            //TODO 这里可以配合线程池
            //TODO 发送失败应该发送一条消息给MQ进行补偿
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Async
    public void sendOfficalEmail(String email, String subject, String content, Boolean isHtml, File[] files) {
        //配置MailAccount对象 hutool提供的
        MailAccount mailAccount =
                new MailAccount().setFrom("Towelove官方<460219753@qq.com>").setAuth(true).setUser(officialMailInfo.getUsername()).setPass(officialMailInfo.getPassword()).setHost(officialMailInfo.getHost()).setPort(officialMailInfo.getPort()).setSslEnable(true);
        //发送邮件 msgIG为邮件id
        MailUtil.send(mailAccount, email, subject, content, isHtml, files);

        switch (subject) {
            //当前是一个验证码消息
            case RedisKeyConstant.VALIDATE_CODE_SUBJECT: {
                redisService.setCacheObject(RedisKeyConstant.VALIDATE_CODE + email, content);
                break;
            }
            default: {
                break;
            }
        }
    }

}
