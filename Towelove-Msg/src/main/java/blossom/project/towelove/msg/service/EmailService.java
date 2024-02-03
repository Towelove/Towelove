package blossom.project.towelove.msg.service;

import blossom.project.towelove.common.request.todoList.TodoRemindRequest;
import blossom.project.towelove.common.request.user.InvitedEmailRequest;
import blossom.project.towelove.msg.entity.CompletedMailMsgTask;
import blossom.project.towelove.msg.entity.UserFeedBack;

import java.io.File;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/22 17:00
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MailSendService接口
 */
public interface EmailService {
    String sendCompletedMailMsg(CompletedMailMsgTask msgTask);

    String generateAndSendValidateCode(String email);

    String todoRemind(TodoRemindRequest request);

    /**
     * 发送官方邮件
     * @param email 接收方地址
     * @param subject 邮件标题
     * @param content 邮件内容
     * @param isHtml 是否是html
     * @param files 是否带有附件
     */
    void sendOfficalEmail(String email, String subject, String content, Boolean isHtml, File[] files);

    /**
     * 发送邀请伴侣的短信
     * @param request
     * @return
     */
    String sendInvitedEmail(InvitedEmailRequest request);

    /**
     * 信箱功能：用户建议与反馈信息
     * @param feedbackContent
     * @return
     */
    String userFeedback(UserFeedBack feedbackContent);
}
