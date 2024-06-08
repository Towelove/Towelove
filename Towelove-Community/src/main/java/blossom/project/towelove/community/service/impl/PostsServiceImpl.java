package blossom.project.towelove.community.service.impl;
import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.PostsConvert;
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
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

    private final PostsMapper postsMapper;


    @Override
    public PostsRespDTO createPosts(PostsCreateRequest createRequest) {
        Posts posts = PostsConvert.INSTANCE.convert(createRequest);
        if (Objects.isNull(posts)) {
            log.info("the posts is null...");
            return null;
        }
        postsMapper.insert(posts);
        PostsRespDTO respDTO = PostsConvert.INSTANCE.convert(posts);
        return respDTO;
    }

    @Override
    public PostsRespDTO getPostsById(Long PostsId) {
        return null;
    }

    @Override
    public PostsRespDTO getPostsDetailById(Long postsId) {
        Posts posts = postsMapper.selectById(postsId);
        if (Objects.isNull(posts)) {
            log.info("the posts is null...");
            return null;
        }
        PostsRespDTO respDTO = PostsConvert.INSTANCE.convert(posts);
        return respDTO;
    }

    @Override
    public PageResponse<PostsRespDTO> pageQueryPosts(PostsPageRequest pageRequest) {
        LambdaQueryWrapper<Posts> lqw = new LambdaQueryWrapper<>();
        Page<Posts> page = new Page(pageRequest.getPageNo(), pageRequest.getPageSize());
        Page<Posts> postsPage = postsMapper.selectPage(page, lqw);
        List<PostsRespDTO> respDTOList = null;
        if (CollectionUtil.isEmpty(postsPage.getRecords())) {
            respDTOList = PostsConvert.INSTANCE.convert(postsPage.getRecords());
        }
        return new PageResponse<>(pageRequest.getPageNo(), pageRequest.getPageSize(), respDTOList);
    }

    @Override
    public PostsRespDTO updatePosts(PostsUpdateRequest updateRequest) {
        Posts posts = postsMapper.selectById(updateRequest.getId());
        if (Objects.isNull(posts)) {
            log.error("the album is null");
            throw new EntityNotFoundException("can not find posts whick id is: " + updateRequest.getId()
                    , BaseErrorCode.ENTITY_NOT_FOUNT);
        }
        try {
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



