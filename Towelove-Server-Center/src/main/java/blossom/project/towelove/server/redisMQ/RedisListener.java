package blossom.project.towelove.server.redisMQ;

import blossom.project.towelove.common.entity.notification.Notification;
import blossom.project.towelove.framework.redis.service.RedisService;
import blossom.project.towelove.server.service.NotificationService;
import blossom.project.towelove.server.service.impl.NotificationServiceImpl;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.redisMQ
 * @className: RedisListener
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 18:38
 * @version: 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisListener implements InitializingBean {

    private final StringRedisTemplate redisTemplate;

    private final NotificationServiceImpl notificationService;

    ScheduledExecutorService scheduler =  Executors.newScheduledThreadPool(1, r -> new Thread(r,"notifyListener"));

    @Override
    public void afterPropertiesSet() {
        /**
         * 100毫秒到队列中读取一次
         */
        scheduler.scheduleAtFixedRate(() ->{
            String message = redisTemplate.opsForList().leftPop(UserNotifyConstants.USER_NOTIFY_QUEUE);
            Notification notification = JSON.parseObject(message, Notification.class);
            if (notification != null) {
                notificationService.setDeferredResult(notification.getRequestId(),notification.getMessage());
            }
        },5,100, TimeUnit.MILLISECONDS);
        log.info("notifyListener start successfully");
    }
}
