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
public class PostLikesServiceImpl extends ServiceImpl<PostLikesMapper, PostLikes> implements PostLikesService {

    private final PostLikesMapper postLikesMapper;


    @Override
    public PostLikesRespDTO createPostLikes(PostLikesCreateRequest createRequest) {
        PostLikes postLikes = PostLikesConvert.INSTANCE.convert(createRequest);
        if (Objects.isNull(postLikes)) {
            log.info("the postLikes is null...");
            return null;
        }
        postLikesMapper.insert(postLikes);
        PostLikesRespDTO respDTO = PostLikesConvert.INSTANCE.convert(postLikes);
        return respDTO;
    }

    @Override
    public PostLikesRespDTO getPostLikesById(Long PostLikesId) {
        return null;
    }

    @Override
    public PostLikesRespDTO getPostLikesDetailById(Long postLikesId) {
        PostLikes postLikes = postLikesMapper.selectById(postLikesId);
        if (Objects.isNull(postLikes)) {
            log.info("the postLikes is null...");
            return null;
        }
        PostLikesRespDTO respDTO = PostLikesConvert.INSTANCE.convert(postLikes);
        return respDTO;
    }

    @Override
    public PageResponse<PostLikesRespDTO> pageQueryPostLikes(PostLikesPageRequest pageRequest) {
        LambdaQueryWrapper<PostLikes> lqw = new LambdaQueryWrapper<>();
        Page<PostLikes> page = new Page(pageRequest.getPageNo(), pageRequest.getPageSize());
        Page<PostLikes> postLikesPage = postLikesMapper.selectPage(page, lqw);
        List<PostLikesRespDTO> respDTOList = null;
        if (CollectionUtil.isEmpty(postLikesPage.getRecords())) {
            respDTOList = PostLikesConvert.INSTANCE.convert(postLikesPage.getRecords());
        }
        return new PageResponse<>(pageRequest.getPageNo(), pageRequest.getPageSize(), respDTOList);
    }

    @Override
    public PostLikesRespDTO updatePostLikes(PostLikesUpdateRequest updateRequest) {
        PostLikes postLikes = postLikesMapper.selectById(updateRequest.getId());
        if (Objects.isNull(postLikes)) {
            log.error("the album is null");
            throw new EntityNotFoundException("can not find postLikes whick id is: " + updateRequest.getId()
                    , BaseErrorCode.ENTITY_NOT_FOUNT);
        }
        try {
            postLikesMapper.updateById(postLikes);
            PostLikes resp = postLikesMapper.selectById(postLikes.getId());
            PostLikesRespDTO respDTO = PostLikesConvert.INSTANCE.convert(resp);
            return respDTO;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Boolean deletePostLikesById(Long postLikesId) {
        return (postLikesMapper.deleteById(postLikesId)) > 0;
    }

    @Override
    public Boolean batchDeletePostLikes(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            log.info("ids is null...");
            return true;
        }
        return postLikesMapper.deleteBatchIds(ids) > 0;
    }

}



