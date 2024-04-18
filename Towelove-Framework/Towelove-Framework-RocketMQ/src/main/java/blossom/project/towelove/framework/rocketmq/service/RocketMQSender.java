package blossom.project.towelove.framework.rocketmq.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

//@AutoConfiguration
public class RocketMQSender {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 同步发送消息
     *
     * @param topic   消息主题
     * @param message 消息内容
     */
    public void syncSend(String topic, String message) {
        rocketMQTemplate.syncSend(topic, message);
    }

    /**
     * 异步发送消息
     *
     * @param topic        消息主题
     * @param message      消息内容
     * @param sendCallback 发送回调
     */
    public void asyncSend(String topic, String message, SendCallback sendCallback) {
        rocketMQTemplate.asyncSend(topic, message, sendCallback);
    }

    /**
     * 发送单向消息
     *
     * @param topic   消息主题
     * @param message 消息内容
     */
    public void sendOneWay(String topic, String message) {
        rocketMQTemplate.sendOneWay(topic, message);
    }

    /**
     * 发送延迟消息
     *
     * @param topic      消息主题
     * @param message    消息内容
     * @param delayLevel 延迟级别
     */
    public void delaySend(String topic, String message, int delayLevel) {
        Message<String> msg = MessageBuilder.withPayload(message).build();
        rocketMQTemplate.syncSend(topic, msg, rocketMQTemplate.getProducer().getSendMsgTimeout(), delayLevel);
    }

    /**
     * 发送有序消息
     *
     * @param topic   消息主题
     * @param data    消息数据
     * @param hashKey 用于消息队列选择的哈希键
     * @param <T>     消息数据类型
     */
    public <T> void sendOrderly(String topic, T data, String hashKey) {
        String json = JSONObject.toJSONString(data);
        rocketMQTemplate.syncSendOrderly(topic, json, hashKey);
    }

    /**
     * 发送带标签的消息
     *
     * @param topic   消息主题
     * @param tag     消息标签
     * @param message 消息内容
     */
    public void sendWithTag(String topic, String tag, String message) {
        rocketMQTemplate.syncSend(topic + ":" + tag, message);
    }

    /**
     * 发送带有键的消息
     *
     * @param topic   消息主题
     * @param key     消息键
     * @param message 消息内容
     */
    public void sendWithKey(String topic, String key, String message) {
        Message<String> msg = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, key).build();
        rocketMQTemplate.syncSend(topic, msg);
    }
}
