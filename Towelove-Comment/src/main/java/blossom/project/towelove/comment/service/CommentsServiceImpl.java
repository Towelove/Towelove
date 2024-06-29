package blossom.project.towelove.comment.service;

import blossom.project.towelove.comment.convert.CommentsConvert;
import blossom.project.towelove.comment.dto.CommentsRespDTO;
import blossom.project.towelove.comment.entity.Comments;
import blossom.project.towelove.comment.mapper.CommentLikesMapper;
import blossom.project.towelove.comment.mapper.CommentsMapper;
import blossom.project.towelove.comment.req.CommentsCreateRequest;
import blossom.project.towelove.comment.req.CommentsPageRequest;
import blossom.project.towelove.common.enums.ShowTags;
import blossom.project.towelove.common.page.PageResponse;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
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

    private final CommentLikesMapper commentLikesMapper;

    @Override
    public CommentsRespDTO createComments(CommentsCreateRequest createRequest) {
        Comments comment = CommentsConvert.INSTANCE.convert(createRequest);
        commentsMapper.insert(comment);

        return CommentsConvert.INSTANCE.convert(comment);
    }

    @Override
    public CommentsRespDTO getCommentsById(Long commentsId) {
        Comments comment = commentsMapper.selectById(commentsId);
        return CommentsConvert.INSTANCE.convert(comment);
    }


    /**
     * 用户第一次看到评论，粗略看一眼，
     * 只展示每一条评论的第一条子评论信息
     * @param requestParam
     * @return
     */
    @Override
    public PageResponse<CommentsRespDTO> glanceQueryComments(CommentsPageRequest requestParam) {
        // 创建分页对象
        Page<Comments> page = new Page<>(requestParam.getPageNo(), requestParam.getPageSize());

        // 查询主评论（parent_id 为 NULL）
        Page<Comments> commentsPage = commentsMapper.selectPage(page, requestParam.getPostId());

        // 将主评论列表转换为 DTO 列表
        List<CommentsRespDTO> commentsRespDTOList = commentsPage.getRecords().stream()
                .map(comment -> {
                    // 获取每条主评论的一个子评论
                    List<Comments> subComments = commentsMapper.selectSubComments(comment.getId(), 1);

                    // 获取子评论总数
                    int totalSubComments = commentsMapper.countSubComments(comment.getId());

                    // 将主评论转换为 DTO
                    CommentsRespDTO commentsRespDTO = CommentsConvert.INSTANCE.convert(comment);

                    // 判断当前用户是否点赞了该评论
                    commentsRespDTO.setLiked(isCommentLikedByUser(comment.getId(), requestParam.getUserId()));  // 设置 liked 字段

                    // 将子评论列表转换为 DTO 列表并设置到主评论的 DTO 中
                    commentsRespDTO.setSubComments(subComments.stream()
                            .map(CommentsConvert.INSTANCE::convert)
                            .collect(Collectors.toList()));

                    // 设置子评论计数和是否有更多子评论
                    commentsRespDTO.setSubCommentCount(totalSubComments - subComments.size());
                    commentsRespDTO.setSubCommentHasMore(subComments.size() < totalSubComments);

                    return commentsRespDTO;
                })
                .collect(Collectors.toList());

        // 返回分页结果
        return new PageResponse<>(
                requestParam.getPageNo(),
                requestParam.getPageSize(),
                commentsRespDTOList
        );
    }


    @Override
    public PageResponse<CommentsRespDTO> pageQueryComments(CommentsPageRequest requestParam) {
        // 分页获取主评论
        Page<Comments> page = new Page<>(requestParam.getPageNo(), requestParam.getPageSize());
        Page<Comments> commentsPage = commentsMapper.selectPage(page, requestParam.getPostId());

        // 将主评论转换为响应DTO，并处理子评论
        List<CommentsRespDTO> commentsRespDTOList = commentsPage.getRecords().stream()
                .map(comment -> {
                    CommentsRespDTO commentsRespDTO = CommentsConvert.INSTANCE.convert(comment);

                    //  获取当前用户是否点赞当前评论
                    // 设置 liked 字段
                    commentsRespDTO.setLiked(isCommentLikedByUser(comment.getId(), requestParam.getUserId()));

                    // 获取主评论的子评论，带分页
                    commentsRespDTO.setSubComments(getSubCommentsWithLimit(comment.getId(), requestParam.getSubPageNo(), requestParam.getSubPageSize()));
                    // 设置子评论计数和是否有更多子评论
                    int totalSubComments = commentsMapper.countSubComments(comment.getId());
                    commentsRespDTO.setSubCommentCount(totalSubComments - commentsRespDTO.getSubComments().size());
                    commentsRespDTO.setSubCommentHasMore(commentsRespDTO.getSubComments().size() < totalSubComments);
                    return commentsRespDTO;
                })
                .sorted(this::compareComments) // 排序主评论
                .collect(Collectors.toList());

        return new PageResponse<>(
                requestParam.getPageNo(),
                requestParam.getPageSize(),
                commentsRespDTOList
        );
    }

    @Override
    public PageResponse<CommentsRespDTO> expandComment(Long parentId, int subPageNo, int subPageSize) {
        List<CommentsRespDTO> subComments = getSubCommentsWithLimit(parentId, subPageNo, subPageSize);
        int total = commentsMapper.countSubComments(parentId); // 获取子评论总数

        // 设置主评论的子评论数量和是否有更多子评论的标志
        int displayedSubComments = (subPageNo - 1) * subPageSize + subComments.size();
        boolean hasMore = displayedSubComments < total;

        // 将子评论加入到主评论的子评论列表中
        CommentsRespDTO parentComment = new CommentsRespDTO();
        parentComment.setSubCommentCount(Math.max(0, total - displayedSubComments));
        parentComment.setSubCommentHasMore(hasMore);
        parentComment.setSubComments(subComments);

        return new PageResponse<>(subPageNo, subPageSize, List.of(parentComment));
    }

    private List<CommentsRespDTO> getSubCommentsWithLimit(Long parentId, int subPageNo, int subPageSize) {
        int offset = (subPageNo - 1) * subPageSize;
        // 获取指定数量的子评论
        List<Comments> subComments = commentsMapper.selectSubCommentsWithLimit(parentId, subPageSize, offset);
        int totalSubComments = commentsMapper.countSubComments(parentId);

        return subComments.stream()
                .map(comment -> {
                    CommentsRespDTO commentsRespDTO = CommentsConvert.INSTANCE.convert(comment);
                    // 设置子评论计数和是否有更多子评论
                    commentsRespDTO.setSubCommentCount(Math.max(0, totalSubComments - offset - subPageSize));
                    commentsRespDTO.setSubCommentHasMore(offset + subPageSize < totalSubComments);
                    // 设置 liked 字段
                    commentsRespDTO.setLiked(isCommentLikedByUser(comment.getId(),
                            comment.getUserId()));

                    return commentsRespDTO;
                })
                .sorted(this::compareComments) // 排序子评论
                .collect(Collectors.toList());
    }

    //  判断当前用户是否点赞了当前评论
    private boolean isCommentLikedByUser(Long commentId, Long userId) {
        return commentLikesMapper.isLikedByUser(commentId, userId) > 0;
    }

    /**
     * 比较两个评论，根据 showTags 进行排序
     * 1. "pinned" 标签的评论排在最前面
     * 2. 按点赞数量排序，点赞数量多的评论排在前面
     * 3. 按创建时间排序，较早的评论排在前面
     * 4. "is_author" 标签的评论排在前面
     */
    private int compareComments(CommentsRespDTO c1, CommentsRespDTO c2) {
        List<String> tags1 = c1.getShowTags();
        List<String> tags2 = c2.getShowTags();
        boolean isPinned1 = tags1 != null && tags1.contains(ShowTags.PINNED.getValue());
        boolean isPinned2 = tags2 != null && tags2.contains(ShowTags.PINNED.getValue());
        boolean isAuthor1 = tags1 != null && tags1.contains(ShowTags.IS_AUTHOR.getValue());
        boolean isAuthor2 = tags2 != null && tags2.contains(ShowTags.IS_AUTHOR.getValue());

        if (isPinned1 && !isPinned2) return -1;
        if (!isPinned1 && isPinned2) return 1;

        // 如果两者都有或都没有 pinned 标签，则按点赞数量排序
        int likes1 = c1.getLikesNum();
        int likes2 = c2.getLikesNum();
        if (likes1 != likes2) return Integer.compare(likes2, likes1);

        // 如果点赞数量相同，则按创建时间排序
        int timeComparison = c1.getCreateTime().compareTo(c2.getCreateTime());
        if (timeComparison != 0) return timeComparison;

        // 如果创建时间也相同，则按是否为作者排序
        if (isAuthor1 && !isAuthor2) return -1;
        if (!isAuthor1 && isAuthor2) return 1;

        return 0;
    }

    @Override
    public Boolean deleteCommentsById(Long commentsId) {
        return commentsMapper.deleteById(commentsId) > 0;
    }

    @Override
    public Boolean batchDeleteComments(@RequestBody List<Long> ids) {
        return commentsMapper.deleteBatchIds(ids) > 0;
    }


}



