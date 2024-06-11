package blossom.project.towelove.community.service.impl;

import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.PostsConvert;
import blossom.project.towelove.community.dto.PostsRespDTO;
import blossom.project.towelove.community.entity.PostFavorites;
import blossom.project.towelove.community.entity.PostLikes;
import blossom.project.towelove.community.entity.Posts;
import blossom.project.towelove.community.entity.inner.InteractInfo;
import blossom.project.towelove.community.mapper.PostsMapper;
import blossom.project.towelove.community.mapper.PostLikesMapper;
import blossom.project.towelove.community.mapper.PostFavoritesMapper;
import blossom.project.towelove.community.req.PostsCreateRequest;
import blossom.project.towelove.community.req.PostsPageRequest;
import blossom.project.towelove.community.req.PostsUpdateRequest;
import blossom.project.towelove.community.service.PostsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 帖子服务实现类
 * 负责帖子相关的业务逻辑
 * 实现了PostsService接口
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

    private final PostsMapper postsMapper;  // 帖子Mapper接口
    private final PostLikesMapper postLikesMapper;  // 帖子点赞Mapper接口
    private final PostFavoritesMapper postFavoritesMapper;  // 帖子收藏Mapper接口

    /**
     * 获取帖子详情，根据帖子ID和用户ID获取详细信息
     *
     * @param postsId 帖子ID
     * @param userId  用户ID
     * @return 帖子详细信息DTO
     */
    @Override
    public PostsRespDTO getPostsDetailById(Long postsId, Long userId) {
        // 从数据库中获取帖子详情
        Posts posts = postsMapper.getPostsDetailById(postsId);
        if (Objects.isNull(posts)) {
            // 如果帖子不存在，抛出异常
            throw new EntityNotFoundException(BaseErrorCode.ENTITY_NOT_FOUND.message(),
                    BaseErrorCode.ENTITY_NOT_FOUND);
        }

        // 获取用户点赞和收藏状态
        boolean isLiked = postLikesMapper.isPostLikedByUser(postsId, userId) > 0;
        boolean isFavorited = postFavoritesMapper.isPostFavoritedByUser(postsId, userId) > 0;

        // 设置 InteractInfo 信息
        InteractInfo interactInfo = posts.getInteractInfo();
        interactInfo.setLiked(isLiked);
        interactInfo.setCollected(isFavorited);

        // 获取点赞和收藏次数
        Long likedCount = postLikesMapper.selectCount(new QueryWrapper<PostLikes>().eq("post_id", postsId).eq("status", 1));
        Long collectedCount = postFavoritesMapper.selectCount(new QueryWrapper<PostFavorites>().eq("post_id", postsId).eq("status", 1));

        interactInfo.setLikedCount(likedCount);
        interactInfo.setCollectedCount(collectedCount);

        // 转换为 DTO 并返回
        PostsRespDTO respDTO = PostsConvert.INSTANCE.convert(posts);
        return respDTO;
    }

    /**
     * 创建帖子
     *
     * @param createRequest 创建帖子请求对象
     * @return 创建后的帖子详细信息DTO
     */
    @Override
    @Transactional
    public PostsRespDTO createPosts(PostsCreateRequest createRequest) {
        // 将创建请求对象转换为帖子实体对象
        Posts posts = PostsConvert.INSTANCE.convert(createRequest);
        if (Objects.isNull(posts)) {
            log.info("the posts is null...");
            return null;
        }
        // 插入帖子数据到数据库
        postsMapper.insert(posts);
        // 返回创建后的帖子详细信息
        return getPostsDetailById(posts.getId(),
                createRequest.getUserInfo().getUserId());
    }

    /**
     * 分页查询帖子
     *
     * @param pageRequest 分页查询请求对象
     * @return 分页查询结果
     */
    @Override
    public PageResponse<PostsRespDTO> pageQueryPosts(PostsPageRequest pageRequest) {
        // 执行分页查询
        List<Posts> postsList = postsMapper.pageQueryPosts(
                pageRequest.getTitle(),
                pageRequest.getNickName(),
                pageRequest.getContent(),
                pageRequest.getTag(),
                pageRequest.getFilters(),
                pageRequest.getPageNo().intValue() - 1,
                pageRequest.getPageSize().intValue(),
                pageRequest.getSortBy() != null ? pageRequest.getSortBy() : null,
                pageRequest.getSortOrder() != null ? pageRequest.getSortOrder() : null
        );

        // 获取当前用户ID
        Long userId = pageRequest.getUserId();

        // 为每篇文章设置 InteractInfo
        List<PostsRespDTO> respDTOList = postsList.stream().map(post -> {
            PostsRespDTO respDTO = PostsConvert.INSTANCE.convert(post);

            // 获取用户点赞状态
            boolean isLiked = postLikesMapper.isPostLikedByUser(post.getId(), userId) > 0;
            int likedCount = postLikesMapper.selectCount(new QueryWrapper<PostLikes>().eq("post_id", post.getId()).eq("status", 1)).intValue();

            // 设置 InteractInfo
            InteractInfo interactInfo = post.getInteractInfo();
            interactInfo.setLiked(isLiked);
            interactInfo.setLikedCount(likedCount);

            // 将设置好的 InteractInfo 放入 respDTO
            respDTO.setInteractInfo(interactInfo);

            return respDTO;
        }).collect(Collectors.toList());

        // 返回分页响应结果
        return new PageResponse<>(
                pageRequest.getPageNo(),
                pageRequest.getPageSize(),
                respDTOList
        );
    }


    /**
     * 更新帖子
     *
     * @param updateRequest 更新帖子请求对象
     * @return 更新后的帖子详细信息DTO
     */
    @Override
    @Transactional
    public PostsRespDTO updatePosts(PostsUpdateRequest updateRequest) {
        // 将更新请求对象转换为帖子实体对象
        Posts posts = PostsConvert.INSTANCE.convert(updateRequest);
        // 更新帖子数据到数据库
        postsMapper.updateById(posts);
        // 返回更新后的帖子详细信息
        return getPostsDetailById(posts.getId(),
                updateRequest.getUserInfo().getUserId());
    }

    /**
     * 根据帖子ID删除帖子
     *
     * @param postsId 帖子ID
     * @return 删除是否成功
     */
    @Override
    @Transactional
    public Boolean deletePostsById(Long postsId) {
        // 从数据库中删除帖子
        return postsMapper.deleteById(postsId) > 0;
    }

    /**
     * 批量删除帖子
     *
     * @param ids 帖子ID列表
     * @return 批量删除是否成功
     */
    @Override
    @Transactional
    public Boolean batchDeletePosts(List<Long> ids) {
        if (ids.isEmpty()) {
            log.info("ids is null...");
            return true;
        }
        // 从数据库中批量删除帖子
        return postsMapper.deleteBatchIds(ids) > 0;
    }
}
