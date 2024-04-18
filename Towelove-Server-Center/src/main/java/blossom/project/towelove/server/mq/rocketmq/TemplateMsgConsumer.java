package blossom.project.towelove.server.mq.rocketmq;

import blossom.project.towelove.server.handler.TemplateHandler;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2024/4/18 5:01 PM
 * TemplateMsgConsumer类
 */
@Component
@RocketMQMessageListener(
        topic = "templateMsgConsumer",
        consumerGroup = "test-consumer-group",
        consumeMode = ConsumeMode.ORDERLY, //顺序消费模式 单线程
        maxReconsumeTimes = 5) //最大重试次数
public class TemplateMsgConsumer implements RocketMQListener<MessageExt> {
    @Autowired
    private TemplateHandler templateHandler;
    @Override
    public void onMessage(MessageExt msg) {
        //得到消息负载
        byte[] body = msg.getBody();
        //转化为字符串
        String templateStr = new String(body);
        //转换为实际可以操作的类型
        TemplateDTO templateDTO = JSONObject.parseObject(templateStr, TemplateDTO.class);
        // 处理接收到的消息
        templateHandler.justTest(templateDTO);
    }
}
