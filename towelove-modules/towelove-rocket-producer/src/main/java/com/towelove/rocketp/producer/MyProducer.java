package com.towelove.rocketp.producer;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2023/3/2 18:28
 * MyProducer类
 */
@Component
public class MyProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendMessage(String topic,String message){
        rocketMQTemplate.convertAndSend(topic,message);
    }
    public void sendMessageInTransaction(String topic,String msg){
        String[]tags = new String[]{"TagA","TagB","TagC","TagD","TagE"};
        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder.withPayload(msg).build();
            String destination = topic+":"+tags[i % tags.length];
            TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(
                    //消息要发送的目的地topic
                    destination,
                    message,
                    //消息携带的业务数据
                    destination
            );
            System.out.println(sendResult);
        }
    }
}
