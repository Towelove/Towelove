package blossom.project.towelove.framework.redis.util;

import blossom.project.towelove.common.entity.notification.NoticeDTO;
import blossom.project.towelove.framework.redis.core.UserNotifyConstants;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
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
@AutoConfiguration
public class UserNotifyProduction {

    private final StringRedisTemplate redisTemplate;





    /**
     * 系统通知消息生产者
     * @param noticeDTO
     */
    public void sendNotifyMessage(NoticeDTO noticeDTO){
        //判断一下目标用户是不是在线
//        Long userId = noticeDTO.getUserId();
//        if (userNotifyDeferredCache.isExist(userId)){
            redisTemplate.opsForList().rightPush(UserNotifyConstants.USER_NOTIFY_QUEUE, JSON.toJSONString(noticeDTO));
//        }else {
//            //存入数据库中
//            redisTemplate.opsForList().rightPush(UserNotifyConstants.USER_NOTIFY_TO_DB_QUEUE,JSON.toJSONString(noticeDTO));
//        }
    }
}
