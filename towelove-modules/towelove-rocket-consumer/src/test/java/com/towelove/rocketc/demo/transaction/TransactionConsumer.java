package com.towelove.rocketc.demo.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/1 8:45
 */
public class TransactionConsumer {

    public static void main(String[] args) throws MQClientException {
        //1.创建消费者对象
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my-consumer-group1");
        //2.指明nameserver的地址
        consumer.setNamesrvAddr("172.16.253.101:9876");
        //3.订阅主题:topic 和过滤消息用的tag表达式
        consumer.subscribe("TopicTest","*");
        //4.创建一个监听器，当broker把消息推过来时调用
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                for (MessageExt msg : msgs) {
//                    System.out.println("收到的消息："+new String(msg.getBody()));
                    System.out.println("收到的消息："+msg);
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5.启动消费者
        consumer.start();
        System.out.println("消费者已启动");
    }
}
