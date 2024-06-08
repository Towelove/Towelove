package blossom.project.towelove.community.service.impl;
import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.PostTagsConvert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import blossom.project.towelove.community.entity.PostTags;
import blossom.project.towelove.community.mapper.PostTagsMapper;
import blossom.project.towelove.community.service.PostTagsService;
import blossom.project.towelove.community.dto.PostTagsRespDTO;
import blossom.project.towelove.community.req.PostTagsCreateRequest;
import blossom.project.towelove.community.req.PostTagsPageRequest;
import blossom.project.towelove.community.req.PostTagsUpdateRequest;
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
public class PostTagsServiceImpl extends
        ServiceImpl<PostTagsMapper, PostTags> implements PostTagsService {

    private final PostTagsMapper postTagsMapper;


    @Override
    public PostTagsRespDTO createPostTags(PostTagsCreateRequest createRequest) {
        PostTags postTags = PostTagsConvert.INSTANCE.convert(createRequest);
        if (Objects.isNull(postTags)) {
            log.info("the postTags is null...");
            return null;
        }
        postTagsMapper.insert(postTags);
        PostTagsRespDTO respDTO = PostTagsConvert.INSTANCE.convert(postTags);
        return respDTO;
    }

    @Override
    public PostTagsRespDTO getPostTagsById(Long PostTagsId) {
        return null;
    }

    @Override
    public PostTagsRespDTO getPostTagsDetailById(Long postTagsId) {
        PostTags postTags = postTagsMapper.selectById(postTagsId);
        if (Objects.isNull(postTags)) {
            log.info("the postTags is null...");
            return null;
        }
        PostTagsRespDTO respDTO = PostTagsConvert.INSTANCE.convert(postTags);
        return respDTO;
    }

    @Override
    public PageResponse<PostTagsRespDTO> pageQueryPostTags(PostTagsPageRequest pageRequest) {
        LambdaQueryWrapper<PostTags> lqw = new LambdaQueryWrapper<>();
        Page<PostTags> page = new Page(pageRequest.getPageNo(), pageRequest.getPageSize());
        Page<PostTags> postTagsPage = postTagsMapper.selectPage(page, lqw);
        List<PostTagsRespDTO> respDTOList = null;
        if (CollectionUtil.isEmpty(postTagsPage.getRecords())) {
            respDTOList = PostTagsConvert.INSTANCE.convert(postTagsPage.getRecords());
        }
        return new PageResponse<>(pageRequest.getPageNo(), pageRequest.getPageSize(), respDTOList);
    }

    @Override
    public PostTagsRespDTO updatePostTags(PostTagsUpdateRequest updateRequest) {
        PostTags postTags = postTagsMapper.selectById(updateRequest.getId());
        if (Objects.isNull(postTags)) {
            log.error("the album is null");
            throw new EntityNotFoundException("can not find postTags whick id is: " + updateRequest.getId()
                    , BaseErrorCode.ENTITY_NOT_FOUNT);
        }
        try {
            postTagsMapper.updateById(postTags);
            PostTags resp = postTagsMapper.selectById(postTags.getPostId());
            PostTagsRespDTO respDTO = PostTagsConvert.INSTANCE.convert(resp);
            return respDTO;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Boolean deletePostTagsById(Long postTagsId) {
        return (postTagsMapper.deleteById(postTagsId)) > 0;
    }

    @Override
    public Boolean batchDeletePostTags(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            log.info("ids is null...");
            return true;
        }
        return postTagsMapper.deleteBatchIds(ids) > 0;
    }

}



