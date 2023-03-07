package com.towelove.system.service.mail;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.towelove.system.domain.mail.MailConfiguration;
import com.towelove.system.mq.message.mail.MailSendMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: 张锦标
 * @date: 2023/3/7 17:30
 * SendMailTest类
 */
@SpringBootTest
public class SendMailTest {
    @Autowired
    private MailSendService mailSendService;
    @Autowired
    private MailConfiguration mailConfiguration;

    @Test
    public void sendMail() {
        System.out.println(mailConfiguration);
        MailSendMessage message = new MailSendMessage();
        message.setAccountId(1L);
        message.setContent("这是一份测试邮件");
        message.setLogId(1L);
        message.setMail("460219753@qq.com");
        message.setNickname("zjb");
        message.setTitle("测试邮件");
        mailSendService.doSendMail(message);
        //

    }


    @Test
    public void testDemo() {
        MailAccount mailAccount = new MailAccount()
//                .setFrom("奥特曼 <ydym_test@163.com>")
                .setFrom("460219753@qq.com") // 邮箱地址
                .setHost("smtp.qq.com").setPort(465).setSslEnable(true) // SMTP 服务器
                .setAuth(true).setUser("460219753@qq.com").setPass("xxayxcbswxorbggb"); // 登录账号密码
        String messageId = MailUtil.send(mailAccount, "460219753@qq.com",
                "主题", "内容", false);
        System.out.println("发送结果：" + messageId);
    }

    public static void main(String[] args) {
        MailAccount mailAccount = new MailAccount()
//                .setFrom("奥特曼 <ydym_test@163.com>")
                .setFrom("张锦标<460219753@qq.com>") // 邮箱地址
                .setHost("smtp.qq.com").setPort(465).setSslEnable(true) // SMTP 服务器
                .setAuth(true).setUser("460219753@qq.com").setPass("xxayxcbswxorbggb"); // 登录账号密码
        String messageId = MailUtil.send(mailAccount, "460219753@qq.com",
                "主题", "内容", false);
        System.out.println("发送结果：" + messageId);
    }
}
