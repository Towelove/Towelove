package com.towelove.rocketc.demo.base;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/2 10:23
 * BaseConsumer类
 */
public class BaseConsumer {
    public static void main(String[] args) throws MQClientException {
        //1:创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my-consumer-group1");
        //2:指明nameserver的地址
        consumer.setNamesrvAddr("192.168.146.115:9876");
        //3:订阅主题
        consumer.subscribe("MyTopic1","*");
        //4:创建一个监听器,当broker把消息推过来的时候触发
        consumer.registerMessageListener(new MessageListenerOrderly() {
            //将会调用这个里面的方法
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for (MessageExt msg : list) {
                    System.out.println("收到消息"+new String(msg.getBody()));
                }
                //返回我们的处理结果
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //5:启动消费者
        consumer.start();

    }
}
