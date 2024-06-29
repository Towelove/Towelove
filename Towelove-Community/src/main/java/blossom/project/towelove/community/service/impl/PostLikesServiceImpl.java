package blossom.project.towelove.community.service.impl;
import blossom.project.towelove.community.entity.UserBehaviors;
import blossom.project.towelove.common.enums.BehaviorsType;
import blossom.project.towelove.community.mapper.UserBehaviorsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import blossom.project.towelove.community.entity.PostLikes;
import blossom.project.towelove.community.mapper.PostLikesMapper;
import blossom.project.towelove.community.service.PostLikesService;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 22:44:29
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostLikesServiceImpl extends ServiceImpl<PostLikesMapper, PostLikes>
        implements PostLikesService {

    private final PostLikesMapper postLikesMapper;

    private final UserBehaviorsMapper userBehaviorsMapper;

    @Override
    public void likePost(Long postId, Long userId) {
        PostLikes postLikes = new PostLikes();
        postLikes.setUserId(userId);
        postLikes.setPostId(postId);
        postLikes.setStatus(1);
        postLikesMapper.insert(postLikes);

        likePostBehavior(userId,postId);
    }


    private void likePostBehavior(Long userId, Long postId) {
        // 插入用户行为记录
        UserBehaviors userBehaviors = new UserBehaviors();
        userBehaviors.setUserId(userId);
        userBehaviors.setPostId(postId);
        userBehaviors.setBehaviorType(BehaviorsType.LIKE.getValue());
        userBehaviorsMapper.insert(userBehaviors);
    }


    @Override
    public void unlikePost(Long postId, Long userId) {
        postLikesMapper.unlikePost(postId, userId);
    }

    @Override
    public boolean isPostLikedByUser(Long postId, Long userId) {
        return postLikesMapper.isPostLikedByUser(postId, userId) > 0;
    }

}



