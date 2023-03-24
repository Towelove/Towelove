package com.towelove.system.service.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

/**
 * @author: 张锦标
 * @date: 2023/3/22 18:36
 * MailAccountTest类
 */
@SpringBootTest
public class MailAccountTest {
    @Autowired
    private MailAccountService mailAccountService;

    @Test
    public void testAsyncWith() throws ExecutionException, InterruptedException {
        //mailAccountService.exectorWithWrong();
        mailAccountService.testAsyncWithCallBack();
        // sleep 1 秒，保证异步调用的执行
        Thread.sleep(1000);
    }

}
