package blossom.project.towelove.server.redisMQ;

import blossom.project.towelove.common.entity.notification.Notification;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.redisMQ
 * @className: UserNotifyProductor
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 18:46
 * @version: 1.0
 */
@Component
@RequiredArgsConstructor
public class UserNotifyProduction {

    private final StringRedisTemplate redisTemplate;

    public void sendMessage(Notification notification){
        redisTemplate.opsForList().rightPush(UserNotifyConstants.USER_NOTIFY_QUEUE, JSON.toJSONString(notification));
    }

}
