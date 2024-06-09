package blossom.project.towelove.framework.log.mq;


import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;


/**
 * @author: 张锦标
 * @date: 2023/9/30 13:55
 * LogProducer类
 */
//@AutoConfiguration
public class LoveLogProducer {

    private static final Logger logger = LoggerFactory.getLogger(LoveLogProducer.class);

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    //
    @Value("${rocketmq.log.destination}")
    private String destination;

    public void sendNormalLog(String requestId,String logJson){
        //放MQ进行异步处理
        rocketMQTemplate.asyncSend(destination, logJson, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
            }

            @Override
            public void onException(Throwable throwable) {
                logger.error("requestId："+requestId+"消息发送失败,报错信息为："
                        +throwable.getMessage());
            }
        });
        System.out.println(requestId);
        System.out.println(logJson);
    }

    public void sendErrorLog(String requestId,String logJson){
        Message<String> message = MessageBuilder.withPayload(logJson)
                .setHeader(RocketMQHeaders.KEYS, requestId)
                .build();
        rocketMQTemplate.syncSend(destination, message);
        System.out.println(requestId);
        System.out.println(logJson);
    }
}
