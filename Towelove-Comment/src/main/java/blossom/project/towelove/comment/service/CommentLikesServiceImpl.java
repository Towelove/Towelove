package blossom.project.towelove.comment.service;

import blossom.project.towelove.comment.entity.CommentLikes;
import blossom.project.towelove.comment.mapper.CommentLikesMapper;
import blossom.project.towelove.comment.mapper.CommentsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentLikesServiceImpl extends
        ServiceImpl<CommentLikesMapper, CommentLikes> implements CommentLikesService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final CommentsMapper commentsMapper;
    private final CommentLikesMapper commentLikesMapper;

    public static final String COMMENT_LIKE_COUNT_PREFIX = "comment_like_count:";
    private static final String COMMENT_LIKE_USER_PREFIX = "comment_like_user:";

    @Override
    public void likeComment(Long commentId, Long userId) {
        // 更新 Redis 中的点赞数
        String likeCountKey = COMMENT_LIKE_COUNT_PREFIX + commentId;
        redisTemplate.opsForValue().increment(likeCountKey);

        // 更新 Redis 中的用户点赞记录
        String likeUserKey = COMMENT_LIKE_USER_PREFIX + commentId;
        redisTemplate.opsForSet().add(likeUserKey, userId);

        // 插入点赞记录到数据库
        commentLikesMapper.insertLike(commentId, userId);

        // 异步更新数据库
        CompletableFuture.runAsync(() -> updateLikeCountInDatabase(commentId));
    }

    @Override
    public void unlikeComment(Long commentId, Long userId) {
        // 更新 Redis 中的点赞数
        String likeCountKey = COMMENT_LIKE_COUNT_PREFIX + commentId;
        redisTemplate.opsForValue().decrement(likeCountKey);

        // 更新 Redis 中的用户点赞记录
        String likeUserKey = COMMENT_LIKE_USER_PREFIX + commentId;
        redisTemplate.opsForSet().remove(likeUserKey, userId);

        // 删除点赞记录从数据库
        commentLikesMapper.deleteLike(commentId, userId);

        // 异步更新数据库
        CompletableFuture.runAsync(() -> updateLikeCountInDatabase(commentId));
    }

    @Override
    public boolean isCommentLiked(Long commentId, Long userId) {
        String likeUserKey = COMMENT_LIKE_USER_PREFIX + commentId;
        return redisTemplate.opsForSet().isMember(likeUserKey, userId);
    }

    private void updateLikeCountInDatabase(Long commentId) {
        // 从 Redis 获取点赞数
        String likeCountKey = COMMENT_LIKE_COUNT_PREFIX + commentId;
        Integer likeCount = (Integer) redisTemplate.opsForValue().get(likeCountKey);

        if (likeCount != null) {
            // 更新数据库中的点赞数
            commentsMapper.updateLikeCount(commentId, likeCount);
        }
    }
}
