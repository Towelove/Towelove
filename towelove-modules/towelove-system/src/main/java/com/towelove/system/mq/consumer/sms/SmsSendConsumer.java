package com.towelove.system.mq.consumer.sms;

import com.towelove.system.mq.message.sms.SmsSendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author: 张锦标
 * @date: 2023/3/8 17:33
 * SmsSendConsumer类
 */
@Component
@Slf4j
public class SmsSendConsumer implements Consumer<SmsSendMessage>
{
    @Override
    public void accept(SmsSendMessage message) {
        System.out.println(message);
    }

    @Bean
    public Consumer<String> sendSmsToAdmin() {
        return reqest -> {
            log.info("received: {} ", reqest);
        };
    }

    @Bean
    public Consumer<String> sendSmsToUser(){
        return request -> {
            log.info("received: {}", request);
            List<Long> params = Arrays.stream(request.split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            System.out.println(params);
        };
    }

}
