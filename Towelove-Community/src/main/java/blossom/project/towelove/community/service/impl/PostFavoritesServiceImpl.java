package blossom.project.towelove.community.service.impl;
import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.PostFavoritesConvert;
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


    @Override
    public PostFavoritesRespDTO createPostFavorites(PostFavoritesCreateRequest createRequest) {
        PostFavorites postFavorites = PostFavoritesConvert.INSTANCE.convert(createRequest);
        if (Objects.isNull(postFavorites)) {
            log.info("the postFavorites is null...");
            return null;
        }
        postFavoritesMapper.insert(postFavorites);
        PostFavoritesRespDTO respDTO = PostFavoritesConvert.INSTANCE.convert(postFavorites);
        return respDTO;
    }

    @Override
    public PostFavoritesRespDTO getPostFavoritesById(Long PostFavoritesId) {
        return null;
    }

    @Override
    public PostFavoritesRespDTO getPostFavoritesDetailById(Long postFavoritesId) {
        PostFavorites postFavorites = postFavoritesMapper.selectById(postFavoritesId);
        if (Objects.isNull(postFavorites)) {
            log.info("the postFavorites is null...");
            return null;
        }
        PostFavoritesRespDTO respDTO = PostFavoritesConvert.INSTANCE.convert(postFavorites);
        return respDTO;
    }

    @Override
    public PageResponse<PostFavoritesRespDTO> pageQueryPostFavorites(PostFavoritesPageRequest pageRequest) {
        LambdaQueryWrapper<PostFavorites> lqw = new LambdaQueryWrapper<>();
        Page<PostFavorites> page = new Page(pageRequest.getPageNo(), pageRequest.getPageSize());
        Page<PostFavorites> postFavoritesPage = postFavoritesMapper.selectPage(page, lqw);
        List<PostFavoritesRespDTO> respDTOList = null;
        if (CollectionUtil.isEmpty(postFavoritesPage.getRecords())) {
            respDTOList = PostFavoritesConvert.INSTANCE.convert(postFavoritesPage.getRecords());
        }
        return new PageResponse<>(pageRequest.getPageNo(), pageRequest.getPageSize(), respDTOList);
    }

    @Override
    public PostFavoritesRespDTO updatePostFavorites(PostFavoritesUpdateRequest updateRequest) {
        PostFavorites postFavorites = postFavoritesMapper.selectById(updateRequest.getId());
        if (Objects.isNull(postFavorites)) {
            log.error("the album is null");
            throw new EntityNotFoundException("can not find postFavorites whick id is: " + updateRequest.getId()
                    , BaseErrorCode.ENTITY_NOT_FOUND);
        }
        try {
            postFavoritesMapper.updateById(postFavorites);
            PostFavorites resp = postFavoritesMapper.selectById(postFavorites.getId());
            PostFavoritesRespDTO respDTO = PostFavoritesConvert.INSTANCE.convert(resp);
            return respDTO;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Boolean deletePostFavoritesById(Long postFavoritesId) {
        return (postFavoritesMapper.deleteById(postFavoritesId)) > 0;
    }

    @Override
    public Boolean batchDeletePostFavorites(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            log.info("ids is null...");
            return true;
        }
        return postFavoritesMapper.deleteBatchIds(ids) > 0;
    }

}



