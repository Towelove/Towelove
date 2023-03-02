package com.towelove.rocketc.demo.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * 顺序消息
 * @author Thor
 * @公众号 Java架构栈
 */
public class OrderProducer {

    public static void main(String[] args) throws Exception {

        //Instantiate with a producer group name.
        MQProducer producer = new DefaultMQProducer("example_group_name");
        //名字服务器的地址已经在环境变量中配置好了：NAMESRV_ADDR=172.16.253.101:9876
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 10; i++) {
            int orderId = i;

            for(int j = 0 ; j <= 5 ; j ++){
                Message msg =
                        new Message("OrderTopicTest", "order_"+orderId, "KEY" + orderId,
                                ("order_"+orderId+" step " + j).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    /**
                     *
                     * @param mqs MQ会自动填充这个参数 代表的是broker上所有的队列
                     * @param msg 要发送的消息
                     * @param arg 我们的第三个参数orderId
                     * @return
                     */
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Integer id = (Integer) arg;
                        int index = id % mqs.size();
                        return mqs.get(index);
                    }
                }, orderId);

                System.out.printf("%s%n", sendResult);
            }
        }
        //server shutdown
        producer.shutdown();
    }

}
