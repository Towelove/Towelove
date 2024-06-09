package blossom.project.towelove.community.service.impl;
import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.CommentsConvert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import blossom.project.towelove.community.entity.Comments;
import blossom.project.towelove.community.mapper.CommentsMapper;
import blossom.project.towelove.community.service.CommentsService;
import blossom.project.towelove.community.dto.CommentsRespDTO;
import blossom.project.towelove.community.req.CommentsCreateRequest;
import blossom.project.towelove.community.req.CommentsPageRequest;
import blossom.project.towelove.community.req.CommentsUpdateRequest;
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
public class CommentsServiceImpl extends
        ServiceImpl<CommentsMapper, Comments> implements CommentsService {

    private final CommentsMapper commentsMapper;

    @Override
    public PageResponse<CommentsRespDTO> pageQueryComments(CommentsPageRequest requestParam) {
        LambdaQueryWrapper<Comments> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comments::getPostId, requestParam.getPostId())
                .orderByDesc(Comments::getPinned)
                .orderByAsc(Comments::getCreateTime);

        Page<Comments> page = new Page<>(requestParam.getPageNo(), requestParam.getPageSize());
        Page<Comments> commentsPage = commentsMapper.selectPage(page, queryWrapper);

        List<CommentsRespDTO> commentsList = commentsPage.getRecords().stream()
                .map(comment -> {
                    CommentsRespDTO dto = new CommentsRespDTO();
                    // 这里可以使用 MapStruct 或手动进行转换
                    dto.setId(comment.getId());
                    dto.setUserId(comment.getUserId());
                    dto.setPostId(comment.getPostId());
                    dto.setContent(comment.getContent());
                    dto.setParentId(comment.getParentId());
                    dto.setCreateTime(comment.getCreateTime());
                    dto.setLikesNum(comment.getLikesNum());
                    dto.setPinned(comment.getPinned());
                    return dto;
                })
                .collect(Collectors.toList());

        return new PageResponse<>(
                commentsPage.getCurrent(),
                commentsPage.getSize(),
                commentsPage.getTotal(),
                commentsList
        );
    }

    @Override
    public CommentsRespDTO createComments(CommentsCreateRequest createRequest) {
        Comments comments = CommentsConvert.INSTANCE.convert(createRequest);
        if (Objects.isNull(comments)) {
            log.info("the comments is null...");
            return null;
        }
        commentsMapper.insert(comments);
        CommentsRespDTO respDTO = CommentsConvert.INSTANCE.convert(comments);
        return respDTO;
    }

    @Override
    public CommentsRespDTO getCommentsById(Long CommentsId) {
        return null;
    }

    @Override
    public CommentsRespDTO getCommentsDetailById(Long commentsId) {
        Comments comments = commentsMapper.selectById(commentsId);
        if (Objects.isNull(comments)) {
            log.info("the comments is null...");
            return null;
        }
        CommentsRespDTO respDTO = CommentsConvert.INSTANCE.convert(comments);
        return respDTO;
    }

    @Override
    public CommentsRespDTO updateComments(CommentsUpdateRequest updateRequest) {
        Comments comments = commentsMapper.selectById(updateRequest.getId());
        if (Objects.isNull(comments)) {
            log.error("the album is null");
            throw new EntityNotFoundException("can not find comments whick id is: " + updateRequest.getId()
                    , BaseErrorCode.ENTITY_NOT_FOUNT);
        }
        try {
            commentsMapper.updateById(comments);
            Comments resp = commentsMapper.selectById(comments.getId());
            CommentsRespDTO respDTO = CommentsConvert.INSTANCE.convert(resp);
            return respDTO;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Boolean deleteCommentsById(Long commentsId) {
        return (commentsMapper.deleteById(commentsId)) > 0;
    }

    @Override
    public Boolean batchDeleteComments(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            log.info("ids is null...");
            return true;
        }
        return commentsMapper.deleteBatchIds(ids) > 0;
    }

}



