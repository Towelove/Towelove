package blossom.project.towelove.community.service.impl;
import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.PostImagesConvert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import blossom.project.towelove.community.entity.PostImages;
import blossom.project.towelove.community.mapper.PostImagesMapper;
import blossom.project.towelove.community.service.PostImagesService;
import blossom.project.towelove.community.dto.PostImagesRespDTO;
import blossom.project.towelove.community.req.PostImagesCreateRequest;
import blossom.project.towelove.community.req.PostImagesPageRequest;
import blossom.project.towelove.community.req.PostImagesUpdateRequest;
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
public class PostImagesServiceImpl extends
        ServiceImpl<PostImagesMapper, PostImages> implements PostImagesService {

    private final PostImagesMapper postImagesMapper;


    @Override
    public PostImagesRespDTO createPostImages(PostImagesCreateRequest createRequest) {
        PostImages postImages = PostImagesConvert.INSTANCE.convert(createRequest);
        if (Objects.isNull(postImages)) {
            log.info("the postImages is null...");
            return null;
        }
        postImagesMapper.insert(postImages);
        PostImagesRespDTO respDTO = PostImagesConvert.INSTANCE.convert(postImages);
        return respDTO;
    }

    @Override
    public PostImagesRespDTO getPostImagesById(Long PostImagesId) {
        return null;
    }

    @Override
    public PostImagesRespDTO getPostImagesDetailById(Long postImagesId) {
        PostImages postImages = postImagesMapper.selectById(postImagesId);
        if (Objects.isNull(postImages)) {
            log.info("the postImages is null...");
            return null;
        }
        PostImagesRespDTO respDTO = PostImagesConvert.INSTANCE.convert(postImages);
        return respDTO;
    }

    @Override
    public PageResponse<PostImagesRespDTO> pageQueryPostImages(PostImagesPageRequest pageRequest) {
        LambdaQueryWrapper<PostImages> lqw = new LambdaQueryWrapper<>();
        Page<PostImages> page = new Page(pageRequest.getPageNo(), pageRequest.getPageSize());
        Page<PostImages> postImagesPage = postImagesMapper.selectPage(page, lqw);
        List<PostImagesRespDTO> respDTOList = null;
        if (CollectionUtil.isEmpty(postImagesPage.getRecords())) {
            respDTOList = PostImagesConvert.INSTANCE.convert(postImagesPage.getRecords());
        }
        return new PageResponse<>(pageRequest.getPageNo(), pageRequest.getPageSize(), respDTOList);
    }

    @Override
    public PostImagesRespDTO updatePostImages(PostImagesUpdateRequest updateRequest) {
        PostImages postImages = postImagesMapper.selectById(updateRequest.getId());
        if (Objects.isNull(postImages)) {
            log.error("the album is null");
            throw new EntityNotFoundException("can not find postImages whick id is: " + updateRequest.getId()
                    , BaseErrorCode.ENTITY_NOT_FOUNT);
        }
        try {
            postImagesMapper.updateById(postImages);
            PostImages resp = postImagesMapper.selectById(postImages.getPostId());
            PostImagesRespDTO respDTO = PostImagesConvert.INSTANCE.convert(resp);
            return respDTO;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Boolean deletePostImagesById(Long postImagesId) {
        return (postImagesMapper.deleteById(postImagesId)) > 0;
    }

    @Override
    public Boolean batchDeletePostImages(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            log.info("ids is null...");
            return true;
        }
        return postImagesMapper.deleteBatchIds(ids) > 0;
    }

}



