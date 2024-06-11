package blossom.project.towelove.community.service.impl;
import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.PostLikesConvert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import blossom.project.towelove.community.entity.PostLikes;
import blossom.project.towelove.community.mapper.PostLikesMapper;
import blossom.project.towelove.community.service.PostLikesService;
import blossom.project.towelove.community.dto.PostLikesRespDTO;
import blossom.project.towelove.community.req.PostLikesCreateRequest;
import blossom.project.towelove.community.req.PostLikesPageRequest;
import blossom.project.towelove.community.req.PostLikesUpdateRequest;
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
public class PostLikesServiceImpl extends ServiceImpl<PostLikesMapper, PostLikes>
        implements PostLikesService {

    private final PostLikesMapper postLikesMapper;


    @Override
    public void likePost(Long postId, Long userId) {
        postLikesMapper.likePost(postId, userId);
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



