package blossom.project.towelove.msg.service;

import blossom.project.towelove.msg.entity.CompletedMailMsgTask;

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

    String generateValidateCode(String email);
}
