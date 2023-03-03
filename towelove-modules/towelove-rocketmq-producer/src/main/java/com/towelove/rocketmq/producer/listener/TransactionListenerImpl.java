package com.towelove.rocketmq.producer.listener;

import com.towelove.common.core.utils.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author: 张锦标
 * @date: 2023/3/2 20:02
 * TransactionListenerImpl类
 */
@Component
@RocketMQTransactionListener(
        rocketMQTemplateBeanName = "rocketMQTemplate"
)
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        String destination = (String) o;
        //把spring的message转换为rocketmq的message
        org.apache.rocketmq.common.message.Message msg = RocketMQUtil.convertToRocketMessage(
                new StringMessageConverter(),
                "utf-8",
                destination,
                message);
        String tags = msg.getTags();
        if (StringUtils.contains(tags,"TagA")){
            return RocketMQLocalTransactionState.COMMIT;
        }else if(StringUtils.contains(tags,"TagB")){
            return RocketMQLocalTransactionState.ROLLBACK;
        }else{
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
      return null;
    }
}
