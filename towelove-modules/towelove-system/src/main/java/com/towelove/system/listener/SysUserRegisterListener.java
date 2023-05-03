package com.towelove.system.listener;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.towelove.common.core.domain.OfficialMailInfo;
import com.towelove.system.api.domain.SysUser;
import com.towelove.system.event.SysUserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2023/3/8 13:43
 * SysUserRegisterListener类
 */
@Slf4j
@Component
public class SysUserRegisterListener implements
        ApplicationListener<SysUserRegisterEvent> {
    /**
     * 事件监听 收到用户注册的事件之后
     * 发送邮件给对应的用户
     * 使用@Async表示当前任务异步执行
     * 虽然邮件发送可能比较慢，但是这是非关键逻辑
     * @param event the event to respond to
     */
    @Autowired
    private OfficialMailInfo officialMailInfo;
    @Override
    @Async
    public void onApplicationEvent(SysUserRegisterEvent event) {
        SysUser sysUser = event.getSysUser();
        String email = sysUser.getEmail();
        MailAccount mailAccount = new MailAccount()
                .setFrom("Towelove官方<460219753@qq.com>") // 邮箱地址
                .setHost(officialMailInfo.getHost())
                .setPort(officialMailInfo.getPort()).setSslEnable(true) //
                // SMTP 服务器
                .setAuth(true).setUser(officialMailInfo.getUsername())
                .setPass(officialMailInfo.getPassword()); // 登录账号密码
        String messageId = MailUtil.send(mailAccount, email,
                "Towelove官方感谢您的注册", "欢迎您使用我们的开发的项目，" +
                        "我们的联系方法为VX:15377920718，如有问题，请您联系", false);
    }
}
