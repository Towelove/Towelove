package blossom.project.towelove.server.mq.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

//应该考虑用这种方法 而不是直接用springboot包装过的rocketmq
//如果消费者返回 RECONSUME_LATER，RocketMQ 将会自动重新发送该消息进行重试。
// 默认情况下，RocketMQ 会有重试策略，也可以通过设置消费者的 maxReconsumeTimes
// 属性来调整最大重试次数。
public class Consumer {
    public static void test(String[] args) throws Exception {
        // 创建消费者，指定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("your_consumer_group");
        // 设置NameServer地址
        consumer.setNamesrvAddr("your_nameserver_address");
        // 订阅主题和标签
        consumer.subscribe("your_topic", "*");
        
        // 设置消息监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    try {
                        // 处理消息
                        String body = new String(msg.getBody(), "UTF-8");
                        System.out.println("Receive Message: " + body);
                        // 消息处理逻辑
                        // 如果处理成功
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    } catch (Exception e) {
                        // 如果处理失败
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 启动消费者
        consumer.start();
        System.out.println("Consumer Started.");
    }
}
