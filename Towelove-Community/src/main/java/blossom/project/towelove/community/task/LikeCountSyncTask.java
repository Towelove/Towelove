package blossom.project.towelove.community.task;

import blossom.project.towelove.community.mapper.CommentsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

import static blossom.project.towelove.community.service.impl.CommentLikesServiceImpl.COMMENT_LIKE_COUNT_PREFIX;

//@Component
@RequiredArgsConstructor
public class LikeCountSyncTask {

    private final RedisTemplate<String, Object> redisTemplate;
    private final CommentsMapper commentsMapper;

    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public void syncLikeCountToDatabase() {
        Set<String> keys = redisTemplate.keys(COMMENT_LIKE_COUNT_PREFIX + "*");
        if (keys != null) {
            for (String key : keys) {
                Long commentId = Long.valueOf(key.split(":")[1]);
                Integer likeCount = (Integer) redisTemplate.opsForValue().get(key);
                if (likeCount != null) {
                    commentsMapper.updateLikeCount(commentId, likeCount);
                }
            }
        }
    }
}
