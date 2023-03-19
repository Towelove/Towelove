package com.towelove.system.mapst;

import com.towelove.system.convert.mail.MailAccountConvert;
import com.towelove.system.domain.mail.MailAccountDO;
import com.towelove.system.domain.mail.MailTemplateDO;
import com.towelove.system.domain.mail.vo.account.MailAccountRespVO;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/3/19 22:11
 * Test类
 */
@SpringBootTest
public class Test {
    public static void main(String[] args) {
        MailAccountDO mailAccountDO = MailAccountDO.builder().mail("460219753@qq.com")
                .id(1L).host("465").username("zjb")
                .password("123123123").sslEnable(true)
                .userId(1L).build();

        MailTemplateDO  mailTemplateDO = MailTemplateDO.builder()
                .createTime(new Date()).build();

        MailAccountRespVO map = MailAccountConvert.INSTANCE
                .map(mailTemplateDO, mailAccountDO);
        System.out.println(map);

    }
}
