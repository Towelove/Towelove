package blossom.project.towelove.community.service.impl;

import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.PostsConvert;
import blossom.project.towelove.community.dto.PostsRespDTO;
import blossom.project.towelove.community.entity.posts.Posts;
import blossom.project.towelove.community.mapper.PostsMapper;
import blossom.project.towelove.community.req.PostsCreateRequest;
import blossom.project.towelove.community.req.PostsPageRequest;
import blossom.project.towelove.community.req.PostsUpdateRequest;
import blossom.project.towelove.community.service.PostsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
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

    @Override
    public PostsRespDTO getPostsDetailById(Long postsId) {
        Posts posts = postsMapper.getPostsDetailById(postsId);
        if (Objects.isNull(posts)) {
            throw new EntityNotFoundException(BaseErrorCode.ENTITY_NOT_FOUND.message(),
                    BaseErrorCode.ENTITY_NOT_FOUND);
        }
        return PostsConvert.INSTANCE.convert(posts);
    }

    @Override
    @Transactional
    public PostsRespDTO createPosts(PostsCreateRequest createRequest) {
        Posts posts = PostsConvert.INSTANCE.convert(createRequest);
        if (Objects.isNull(posts)) {
            log.info("the posts is null...");
            return null;
        }
        postsMapper.insert(posts);
        return getPostsDetailById(posts.getId());
    }

    @Override
    public PageResponse<PostsRespDTO> pageQueryPosts(PostsPageRequest pageRequest) {
        List<Posts> postsList = postsMapper.pageQueryPosts(
                pageRequest.getTitle(),
                pageRequest.getNickName(),
                pageRequest.getContent(),
                pageRequest.getTag(),
                pageRequest.getFilters(),
                pageRequest.getPageNo().intValue()-1,
                pageRequest.getPageSize().intValue(),
                pageRequest.getSortBy() != null ? pageRequest.getSortBy() : null,
                pageRequest.getSortOrder() != null ? pageRequest.getSortOrder() : null
        );
        List<PostsRespDTO> respDTOList = PostsConvert.INSTANCE.convert(postsList);
        return new PageResponse<>(
                pageRequest.getPageNo(),
                pageRequest.getPageSize(),
                respDTOList
        );
    }



    @Override
    @Transactional
    public PostsRespDTO updatePosts(PostsUpdateRequest updateRequest) {
        Posts posts = PostsConvert.INSTANCE.convert(updateRequest);
        postsMapper.updateById(posts);
        return getPostsDetailById(posts.getId());
    }

    @Override
    @Transactional
    public Boolean deletePostsById(Long postsId) {
        return postsMapper.deleteById(postsId) > 0;
    }

    @Override
    @Transactional
    public Boolean batchDeletePosts(List<Long> ids) {
        if (ids.isEmpty()) {
            log.info("ids is null...");
            return true;
        }
        return postsMapper.deleteBatchIds(ids) > 0;
    }
}
