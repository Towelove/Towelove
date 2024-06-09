package blossom.project.towelove.community.service.impl;

import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.PostsConvert;
import blossom.project.towelove.community.entity.Comments;
import blossom.project.towelove.framework.redis.service.RedisService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import blossom.project.towelove.community.entity.Posts;
import blossom.project.towelove.community.mapper.PostsMapper;
import blossom.project.towelove.community.service.PostsService;
import blossom.project.towelove.community.dto.PostsRespDTO;
import blossom.project.towelove.community.req.PostsCreateRequest;
import blossom.project.towelove.community.req.PostsPageRequest;
import blossom.project.towelove.community.req.PostsUpdateRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

    private final PostsMapper postsMapper;

    private final RedisService redisService;

    @Override
    public PostsRespDTO createPosts(PostsCreateRequest createRequest) {
        Posts posts = PostsConvert.INSTANCE.convert(createRequest);
        if (Objects.isNull(posts)) {
            log.info("the posts is null...");
            return null;
        }
        postsMapper.insert(posts);
        PostsRespDTO respDTO = getPostsById(posts.getId());
        return respDTO;
    }

    @Override
    public PostsRespDTO getPostsById(Long postsId) {
        Posts posts = postsMapper.selectById(postsId);
        if (Objects.isNull(posts)) {
            throw new EntityNotFoundException(BaseErrorCode.ENTITY_NOT_FOUNT.message(),
                    BaseErrorCode.ENTITY_NOT_FOUNT);
        }
        PostsRespDTO respDTO = PostsConvert.INSTANCE.convert(posts);
        return respDTO;
    }

    @Override
    public PostsRespDTO getPostsDetailById(Long postsId) {
        Posts posts = postsMapper.getPostsDetailById(postsId);
        if (Objects.isNull(posts)) {
            log.info("the posts is null...");
            return null;
        }

        //TODO 获取 Redis 中的点赞和收藏数据

        // 将评论处理成树状结构
        List<Comments> comments = buildCommentTree(posts.getComments());
        posts.setComments(comments);

        // 转换为 DTO
        PostsRespDTO respDTO = PostsConvert.INSTANCE.convert(posts);
        return respDTO;
    }

    private List<Comments> buildCommentTree(List<Comments> comments) {
        if (comments == null) {
            return new ArrayList<>();
        }

        // 将评论列表转换为 Map，以 id 作为键
        Map<Long, Comments> commentMap = comments.stream()
                .collect(Collectors.toMap(Comments::getId, comment -> comment));

        // 存储根评论
        List<Comments> rootComments = new ArrayList<>();

        // 构建树状结构
        for (Comments comment : comments) {
            if (comment.getParentId() == null) {
                rootComments.add(comment);
            } else {
                Comments parentComment = commentMap.get(comment.getParentId());
                if (parentComment != null) {
                    if (parentComment.getReplies() == null) {
                        parentComment.setReplies(new ArrayList<>());
                    }
                    parentComment.getReplies().add(comment);
                }
            }
        }

        // 将置顶评论放在最前面
        List<Comments> pinnedComments = rootComments.stream()
                .filter(comment -> comment.getPinned() == 1)
                .collect(Collectors.toList());
        List<Comments> nonPinnedComments = rootComments.stream()
                .filter(comment -> comment.getPinned() == 0)
                .collect(Collectors.toList());

        pinnedComments.addAll(nonPinnedComments);
        return pinnedComments;
    }


    @Override
    public PageResponse<PostsRespDTO> pageQueryPosts(PostsPageRequest pageRequest) {
        LambdaQueryWrapper<Posts> lqw = new LambdaQueryWrapper<>();
        //TODO 基于用户userId增加推荐算法
        Page<Posts> page = new Page(pageRequest.getPageNo(), pageRequest.getPageSize());
        Page<Posts> postsPage = postsMapper.selectPage(page, lqw);
        List<PostsRespDTO> respDTOList = null;
        if (CollectionUtil.isNotEmpty(postsPage.getRecords())) {
            respDTOList = PostsConvert.INSTANCE.convert(postsPage.getRecords());
        }
        return new PageResponse<>(
                pageRequest.getPageNo(),
                pageRequest.getPageSize(),
                respDTOList);
    }

    @Override
    public PostsRespDTO updatePosts(PostsUpdateRequest updateRequest) {
        try {
            Posts posts = PostsConvert.INSTANCE.convert(updateRequest);
            postsMapper.updateById(posts);
            Posts resp = postsMapper.selectById(posts.getId());
            PostsRespDTO respDTO = PostsConvert.INSTANCE.convert(resp);
            return respDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Boolean deletePostsById(Long postsId) {
        return (postsMapper.deleteById(postsId)) > 0;
    }

    @Override
    public Boolean batchDeletePosts(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            log.info("ids is null...");
            return true;
        }
        return postsMapper.deleteBatchIds(ids) > 0;
    }

}



