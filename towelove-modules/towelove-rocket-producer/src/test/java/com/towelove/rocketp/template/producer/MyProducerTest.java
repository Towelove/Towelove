package com.towelove.rocketp.template.producer;

import com.towelove.rocketp.producer.MyProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: 张锦标
 * @date: 2023/3/2 18:30
 * MyProducerTest类
 */
@SpringBootTest
public class MyProducerTest {
    public static final String TOPIC = "towelove-topic";
    @Autowired
    private MyProducer myProducer;
    @Test
    public void myProducer(){
        myProducer.sendMessage("towelove-topic",
                "hello this is test");
    }
    @Test
    public void transaction(){
        myProducer.sendMessageInTransaction(TOPIC,
                "this is a transaction message");
        System.out.println("发送成功");
    }
}
