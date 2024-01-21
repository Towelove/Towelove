package blossom.project.towelove.server.redisMQ;

import blossom.project.towelove.common.entity.notification.NoticeDTO;
import blossom.project.towelove.framework.redis.core.UserNotifyConstants;
import blossom.project.towelove.server.entity.Notice;
import blossom.project.towelove.server.mapper.NoticeMapper;
import blossom.project.towelove.server.service.impl.NotificationServiceImpl;
import com.alibaba.fastjson2.JSON;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
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

    @Getter
    private final StringRedisTemplate redisTemplate;

    private final NotificationServiceImpl notificationService;

    private final NoticeMapper noticeMapper;

    private final UserNotifyDeferredCache userNotifyDeferredCache;

    ScheduledExecutorService scheduler =  Executors.newScheduledThreadPool(1, r -> new Thread(r,"notifyListener"));

    @Override
    public void afterPropertiesSet() {
        /**
         * 100毫秒到队列中读取一次
         */
        scheduler.scheduleAtFixedRate(() ->{
            String message = redisTemplate.opsForList().leftPop(UserNotifyConstants.USER_NOTIFY_QUEUE);
            NoticeDTO noticeDTO = JSON.parseObject(message, NoticeDTO.class);
            if (Objects.isNull(noticeDTO)) return;
            if (userNotifyDeferredCache.isExist(noticeDTO.getUserId())) {
                log.info("用户：{}在线收到一条通知消息：{}",noticeDTO.getUserId(),noticeDTO.getContent());
                notificationService.setDeferredResult(noticeDTO.getUserId(), noticeDTO.getContent());
            }else{
                log.info("用户：{}离线收到一条通知消息：{}",noticeDTO.getUserId(),noticeDTO.getContent());
                //存入DB中
                Notice notice = new Notice();
                notice.setType(1);
                notice.setUserId(noticeDTO.getUserId());
                notice.setContent(noticeDTO.getContent());
                noticeMapper.insert(notice);
            }
        },5,100, TimeUnit.MILLISECONDS);

//        scheduler.scheduleAtFixedRate(() ->{
//            String message = redisTemplate.opsForList().leftPop(UserNotifyConstants.USER_NOTIFY_TO_DB_QUEUE);
//            NoticeDTO noticeDTO = JSON.parseObject(message, NoticeDTO.class);
//            if (Objects.nonNull(noticeDTO)){
//                log.info("用户：{}离线收到一条通知消息：{}",noticeDTO.getUserId(),noticeDTO.getContent());
//                //存入DB中
//                Notice notice = new Notice();
//                notice.setType(1);
//                notice.setUserId(noticeDTO.getUserId());
//                notice.setContent(noticeDTO.getContent());
//                noticeMapper.insert(notice);
////            }
//        },5,100,TimeUnit.MILLISECONDS);
        log.info("notifyListener start successfully");
    }
}
