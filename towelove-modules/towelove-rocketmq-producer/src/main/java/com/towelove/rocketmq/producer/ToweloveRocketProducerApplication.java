package com.towelove.rocketmq.producer;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: 张锦标
 * @date: 2023/3/1 8:45
 * ToweloveRocketApplication类
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ToweloveRocketProducerApplication implements CommandLineRunner {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Value("${towelove.rocketmq.transTopic}")
    private String springTransTopic;
    @Value("${towelove.rocketmq.topic}")
    private String springTopic;
    public static void main(String[] args) {
        SpringApplication.run(ToweloveRocketProducerApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Send string
        SendResult sendResult = rocketMQTemplate
                .syncSend(springTopic + ":acl", "Hello, ACL Msg!");
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", springTopic, sendResult);

        // Send string with spring Message
        sendResult = rocketMQTemplate.syncSend(springTopic, MessageBuilder
                .withPayload("Hello, World! I'm from spring message & ACL Msg").build());
        System.out.printf("syncSend2 to topic %s sendResult=%s %n", springTopic, sendResult);

        //Send transactional messages
        testTransaction();
    }


    private void testTransaction() throws MessagingException {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            try {

                Message msg = MessageBuilder.withPayload("Hello RocketMQ " + i).
                        setHeader(RocketMQHeaders.TRANSACTION_ID, "KEY_" + i).build();
                SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(
                        springTransTopic + ":" + tags[i % tags.length], msg, null);
                System.out.printf("------ send Transactional msg body = %s , sendResult=%s %n",
                        msg.getPayload(), sendResult.getSendStatus());

                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
