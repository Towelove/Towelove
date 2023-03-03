package com.towelove.rocketmq.consumer.demo.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/1 8:45
 */
public class BatchProducer {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.start();
        String topic = "BatchTest";
        /**
         * 批量消息的发送就是吧多条的消息放入到一个集合里面去
         * 然后一次性发送 那么就会减少与rocketmq的io交互操作
         */
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "TagA", "OrderID001", "Hello world 0".getBytes()));
        messages.add(new Message(topic, "TagA", "OrderID002", "Hello world 1".getBytes()));
        messages.add(new Message(topic, "TagA", "OrderID003", "Hello world 2".getBytes()));
        producer.send(messages);
        producer.shutdown();


    }


}
