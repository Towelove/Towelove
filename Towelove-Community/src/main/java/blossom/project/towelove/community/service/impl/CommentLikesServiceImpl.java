package blossom.project.towelove.community.service.impl;

import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.CommentLikesConvert;
import blossom.project.towelove.community.dto.CommentLikesRespDTO;
import blossom.project.towelove.community.entity.CommentLikes;
import blossom.project.towelove.community.mapper.CommentLikesMapper;
import blossom.project.towelove.community.req.CommentLikesCreateRequest;
import blossom.project.towelove.community.req.CommentLikesPageRequest;
import blossom.project.towelove.community.req.CommentLikesUpdateRequest;
import blossom.project.towelove.community.service.CommentLikesService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:01:30
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CommentLikesServiceImpl extends
        ServiceImpl<CommentLikesMapper, CommentLikes> implements CommentLikesService {

    private final CommentLikesMapper commentLikesMapper;


    @Override
    public CommentLikesRespDTO createCommentLikes(CommentLikesCreateRequest createRequest) {
        CommentLikes commentLikes = CommentLikesConvert.INSTANCE.convert(createRequest);
        if (Objects.isNull(commentLikes)) {
            log.info("the commentLikes is null...");
            return null;
        }
        commentLikesMapper.insert(commentLikes);
        CommentLikesRespDTO respDTO = CommentLikesConvert
                .INSTANCE
                .convert(commentLikes);
        return respDTO;
    }

    @Override
    public CommentLikesRespDTO getCommentLikesDetailById(Long commentLikesId) {
        CommentLikes commentLikes = commentLikesMapper.selectById(commentLikesId);
        if (Objects.isNull(commentLikes)) {
            log.info("the commentLikes is null...");
            return null;
        }
        CommentLikesRespDTO respDTO = CommentLikesConvert.INSTANCE.convert(commentLikes);
        return respDTO;
    }

    @Override
    public CommentLikesRespDTO getCommentLikesById(Long CommentLikesId) {
        return null;
    }

    @Override
    public PageResponse<CommentLikesRespDTO> pageQueryCommentLikes(CommentLikesPageRequest pageRequest) {
        LambdaQueryWrapper<CommentLikes> lqw = new LambdaQueryWrapper<>();
        Page<CommentLikes> page = new Page(pageRequest.getPageNo(), pageRequest.getPageSize());
        Page<CommentLikes> commentLikesPage = commentLikesMapper.selectPage(page, lqw);
        List<CommentLikesRespDTO> respDTOList = null;
        if (CollectionUtil.isEmpty(commentLikesPage.getRecords())) {
            respDTOList = CommentLikesConvert.INSTANCE.convert(commentLikesPage.getRecords());
        }
        return new PageResponse<>(pageRequest.getPageNo(), pageRequest.getPageSize(), respDTOList);
    }

    @Override
    public CommentLikesRespDTO updateCommentLikes(CommentLikesUpdateRequest updateRequest) {
        CommentLikes commentLikes = commentLikesMapper.selectById(updateRequest.getId());
        if (Objects.isNull(commentLikes)) {
            log.error("the album is null");
            throw new EntityNotFoundException("can not find commentLikes whick id is: " + updateRequest.getId()
                    , BaseErrorCode.ENTITY_NOT_FOUNT);
        }
        try {
            commentLikesMapper.updateById(commentLikes);
            CommentLikes resp = commentLikesMapper.selectById(commentLikes.getId());
            CommentLikesRespDTO respDTO = CommentLikesConvert.INSTANCE.convert(resp);
            return respDTO;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Boolean deleteCommentLikesById(Long commentLikesId) {
        return (commentLikesMapper.deleteById(commentLikesId)) > 0;
    }

    @Override
    public Boolean batchDeleteCommentLikes(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            log.info("ids is null...");
            return true;
        }
        return commentLikesMapper.deleteBatchIds(ids) > 0;
    }

}



