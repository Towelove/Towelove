package com.towelove.system.mapst;

import com.towelove.system.ToweloveSystemApplication;
import com.towelove.system.convert.MailLogConvert;
import com.towelove.system.domain.mail.MailLogDO;
import com.towelove.system.domain.mail.vo.log.MailLogRespVO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


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
