package com.towelove.spring.cloud.mq;

import com.towelove.spring.cloud.mq.producer.MyProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: 张锦标
 * @date: 2023/3/2 22:21
 * ProducerTest类
 */
@SpringBootTest
public class ProducerTest {
    @Autowired
    private MyProducer myProducer;
    @Test
    public void send(){
        System.out.println(myProducer);
        myProducer.sendMessage("hello this is springcloud stream - rocketmq");
    }
}
