package com.towelove.common.core.utils;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.towelove.common.core.constant.InvitedMessageConstant;
import com.towelove.common.core.domain.OfficialMailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2023/5/4 13:32
 * OfficialMailUtil类
 */
@Component
public class OfficialMailUtil {
    @Autowired
    private OfficialMailInfo officialMailInfo;
    @Async
    public void sendInvitedMessageToUser(String email,String invitedName) {
        //TODO 将邀请码发送到指定email即可
        // 1. 创建发送账号
        //TODO 真正的发送消息并且记录日志
        //配置MailAccount对象 hutool提供的
        MailAccount mailAccount =
                new MailAccount().setFrom("Towelove官方<460219753@qq.com>")
                        .setAuth(true).setUser(officialMailInfo.getUsername())
                        .setPass(officialMailInfo.getPassword())
                        .setHost(officialMailInfo.getHost())
                        .setPort(officialMailInfo.getPort()).setSslEnable(true);
        //发送邮件 msgIG为邮件id
        String msgId = null;

        //TODO 这里需要完成消息的延时发送
        msgId = MailUtil.send(mailAccount, email, invitedName + "邀请您绑定情侣相册",
                invitedName + InvitedMessageConstant.INVITED_CONTENT + InvitedMessageConstant.TOWELOVE_URL, true);
    }
}
