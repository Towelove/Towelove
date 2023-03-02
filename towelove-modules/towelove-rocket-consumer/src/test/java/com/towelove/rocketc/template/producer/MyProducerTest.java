package com.towelove.rocketc.template.producer;

import com.towelove.rocketc.producer.MyProducer;
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
    @Autowired
    private MyProducer myProducer;
    @Test
    public void myProducer(){
        myProducer.sendMessage("TopicTest",
                "hello this is test");
    }
}
