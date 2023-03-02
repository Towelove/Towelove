package com.towelove.rocketp.demo.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 大批量消息的处理
 * @author: 张锦标
 * @date: 2023/3/1 8:45
 */
public class MaxBatchProducer {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("BatchProducerGroupName");
        producer.start();

        //large batch
        String topic = "BatchTest";
        List<Message> messages = new ArrayList<>(100*1000);
        for (int i = 0; i < 100*1000; i++) {
            messages.add(new Message(topic, "Tag", "OrderID" + i, ("Hello world " + i).getBytes()));
        }
//        producer.send(messages);

        //split the large batch into small ones:
        ListSplitter splitter = new ListSplitter(messages);
        while (splitter.hasNext()) {
            List<Message> listItem = splitter.next();
            producer.send(listItem);
        }
        producer.shutdown();


    }


}
