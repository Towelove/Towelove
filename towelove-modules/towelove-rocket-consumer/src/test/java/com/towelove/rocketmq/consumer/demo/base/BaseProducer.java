package com.towelove.rocketmq.consumer.demo.base;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * @author: 张锦标
 * @date: 2023/3/2 10:16
 * BaseProducer类
 */
public class BaseProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException,
            InterruptedException {
        //1:创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("my-producer-group1");
        //2:指定nameserver的地址
        producer.setNamesrvAddr("192.168.146.115:9876");
        //3:启动生产者
        producer.start();
        //4:创建消息
        for (int i = 0; i < 10; i++) {
            Message message = new Message(
                    "MyTopic1",
                    "TagA",
                    ("hello rocketmq"+i).getBytes(StandardCharsets.UTF_8)
            );
            //5:发送消息
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }
        //6:关闭生产者
        producer.shutdown();
    }
}
