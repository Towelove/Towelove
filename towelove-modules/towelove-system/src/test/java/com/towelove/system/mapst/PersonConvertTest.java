package com.towelove.system.mapst;

import com.towelove.system.convert.mail.MailLogConvert;
import com.towelove.system.domain.mail.MailLogDO;
import com.towelove.system.domain.mail.vo.log.MailLogRespVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class PersonConvertTest {
	@Autowired
    private MailLogConvert mailLogConvert;

    @Test
    public void test(){
        MailLogDO mailLogDO = new MailLogDO();
        System.out.println(mailLogDO);
        MailLogRespVO mailLogRespVO = mailLogConvert.convert(mailLogDO);
        System.out.println(mailLogRespVO);
    }
}
