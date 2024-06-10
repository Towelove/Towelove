package blossom.project.towelove.community.service.impl;

import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.CommentsConvert;
import blossom.project.towelove.community.convert.PostsConvert;
import blossom.project.towelove.community.dto.PostsRespDTO;
import blossom.project.towelove.community.entity.posts.Posts;
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
import org.springframework.web.bind.annotation.RequestBody;

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
public class CommentsServiceImpl extends
        ServiceImpl<CommentsMapper, Comments> implements CommentsService {

    private final CommentsMapper commentsMapper;

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
     * 第一次查询，之战时少量评论数据
     * @param requestParam
     * @return
     */
    public PageResponse<CommentsRespDTO> firstQueryComments(CommentsPageRequest requestParam) {
        // 创建分页对象
        Page<Comments> page = new Page<>(requestParam.getPageNo(), requestParam.getPageSize());

        // 查询主评论（parent_id 为 NULL）
        Page<Comments> commentsPage = commentsMapper.selectPage(page, requestParam.getPostId());

        // 将主评论列表转换为 DTO 列表
        List<CommentsRespDTO> commentsRespDTOList = commentsPage.getRecords().stream()
                .map(comment -> {
                    // 获取每条主评论的一个子评论
                    List<Comments> subComments = commentsMapper.selectSubComments(comment.getId(), 1);

                    // 将主评论转换为 DTO
                    CommentsRespDTO commentsRespDTO = CommentsConvert.INSTANCE.convert(comment);

                    // 将子评论列表转换为 DTO 列表并设置到主评论的 DTO 中
                    commentsRespDTO.setSubComments(subComments.stream()
                            .map(CommentsConvert.INSTANCE::convert)
                            .collect(Collectors.toList()));

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
        Page<Comments> page = new Page<>(requestParam.getPageNo(), requestParam.getPageSize());
        Page<Comments> commentsPage = commentsMapper.selectPage(page, requestParam.getPostId());

        List<CommentsRespDTO> commentsRespDTOList = commentsPage.getRecords().stream()
                .map(comment -> {
                    CommentsRespDTO commentsRespDTO = CommentsConvert.INSTANCE.convert(comment);
                    commentsRespDTO.setSubComments(getSubCommentsWithLimit(comment.getId(), requestParam.getSubPageNo(), requestParam.getSubPageSize())); // 获取指定数量的子评论
                    return commentsRespDTO;
                })
                .collect(Collectors.toList());

        return new PageResponse<>(
                requestParam.getPageNo(),
                requestParam.getPageSize(),
                commentsRespDTOList
        );
    }

    private List<CommentsRespDTO> getSubCommentsWithLimit(Long parentId, int subPageNo, int subPageSize) {
        int offset = (subPageNo - 1) * subPageSize;
        List<Comments> subComments = commentsMapper.
                selectSubCommentsWithLimit(parentId, subPageSize, offset); // 获取指定数量的子评论
        return subComments.stream()
                .map(comment -> {
                    CommentsRespDTO commentsRespDTO = CommentsConvert.INSTANCE.convert(comment);
                    commentsRespDTO.setSubComments(getSubCommentsWithLimit(comment.getId(), 1, subPageSize)); // 递归获取子评论，每次只加载一页子评论
                    return commentsRespDTO;
                })
                .collect(Collectors.toList());
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



