package blossom.project.towelove.community.service.impl;
import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.PostFavoritesConvert;
import blossom.project.towelove.community.entity.UserBehaviors;
import blossom.project.towelove.community.enums.BehaviorsType;
import blossom.project.towelove.community.mapper.UserBehaviorsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import blossom.project.towelove.community.entity.PostFavorites;
import blossom.project.towelove.community.mapper.PostFavoritesMapper;
import blossom.project.towelove.community.service.PostFavoritesService;
import blossom.project.towelove.community.dto.PostFavoritesRespDTO;
import blossom.project.towelove.community.req.PostFavoritesCreateRequest;
import blossom.project.towelove.community.req.PostFavoritesPageRequest;
import blossom.project.towelove.community.req.PostFavoritesUpdateRequest;
import java.util.List;
import java.util.Objects;

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



