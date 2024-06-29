package blossom.project.towelove.community.service.impl;
import blossom.project.towelove.community.entity.UserBehaviors;
import blossom.project.towelove.common.enums.BehaviorsType;
import blossom.project.towelove.community.mapper.UserBehaviorsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import blossom.project.towelove.community.entity.PostFavorites;
import blossom.project.towelove.community.mapper.PostFavoritesMapper;
import blossom.project.towelove.community.service.PostFavoritesService;

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
public class PostFavoritesServiceImpl extends
        ServiceImpl<PostFavoritesMapper, PostFavorites> implements PostFavoritesService {

    private final PostFavoritesMapper postFavoritesMapper;

    private final UserBehaviorsMapper userBehaviorsMapper;

    @Override
    public void favoritePost(Long postId, Long userId) {
        postFavoritesMapper.favoritePost(postId, userId);
        favoritePostBehavior(userId,postId);
    }

    private void favoritePostBehavior(Long userId, Long postId) {
        // 插入用户行为记录
        UserBehaviors userBehaviors = new UserBehaviors();
        userBehaviors.setUserId(userId);
        userBehaviors.setPostId(postId);
        userBehaviors.setBehaviorType(BehaviorsType.FAVORITE.getValue());
        userBehaviorsMapper.insert(userBehaviors);
    }


    @Override
    public void unfavoritePost(Long postId, Long userId) {
        postFavoritesMapper.unfavoritePost(postId, userId);
    }

    @Override
    public boolean isPostFavoritedByUser(Long postId, Long userId) {
        return postFavoritesMapper.isPostFavoritedByUser(postId, userId) > 0;
    }
}



