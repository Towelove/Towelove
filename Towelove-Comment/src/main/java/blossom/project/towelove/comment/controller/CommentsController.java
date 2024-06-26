package blossom.project.towelove.comment.controller;

import blossom.project.towelove.comment.dto.CommentsRespDTO;
import blossom.project.towelove.comment.req.CommentsCreateRequest;
import blossom.project.towelove.comment.req.CommentsPageRequest;
import blossom.project.towelove.comment.service.CommentsService;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.response.Result;

import blossom.project.towelove.framework.log.annotation.LoveLog;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 评论控制器
 * 处理评论相关的HTTP请求
 *
 * @autor: ZhangBlossom
 * @date: 2024-06-10
 */
@LoveLog
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    /**
     * 创建评论
     *
     * @param createRequest 评论创建请求
     * @return 创建的评论信息
     */
    @PostMapping("")
    public Result<CommentsRespDTO> createComments(@RequestBody @Valid CommentsCreateRequest createRequest) {
        return Result.ok(commentsService.createComments(createRequest));
    }

    /**
     * 获取评论详情
     *
     * @param commentId 评论ID
     * @return 评论详情信息
     */
    @GetMapping("/{commentId}")
    public Result<CommentsRespDTO> getCommentDetailById(@PathVariable Long commentId) {
        CommentsRespDTO result = commentsService.getCommentsById(commentId);
        return Result.ok(result);
    }

    /**
     * 分页查询评论
     *
     * @param requestParam 分页查询请求参数
     * @return 分页查询结果
     */
    @PostMapping("/page")
    public Result<PageResponse<CommentsRespDTO>> pageQueryComments(@Validated @RequestBody CommentsPageRequest requestParam) {
        return Result.ok(commentsService.pageQueryComments(requestParam));
    }

    /**
     * 瞥一眼评论区
     * 展示五条评论，且每一条评论只展示其下面的一条子评论
     *
     * @param requestParam
     * @return
     */
    @PostMapping("/glance")
    public Result<PageResponse<CommentsRespDTO>> glanceComments(@Validated @RequestBody CommentsPageRequest requestParam) {
        return Result.ok(commentsService.glanceQueryComments(requestParam));
    }


    /**
     * 展开子评论
     *
     * @return 分页查询结果
     */
    @PostMapping("/expand")
    public Result<PageResponse<CommentsRespDTO>> expandComment(@RequestBody CommentsPageRequest pageRequest) {
        return Result.ok(commentsService.expandComment(
                pageRequest.getParentId(),
                pageRequest.getSubPageNo(),
                pageRequest.getSubPageSize()));
    }


    /**
     * 删除评论
     *
     * @param commentId 评论ID
     * @return 删除结果
     */
    @DeleteMapping("")
    public Result<Boolean> deleteCommentById(@RequestParam @Validated Long commentId) {
        return Result.ok(commentsService.deleteCommentsById(commentId));
    }

    /**
     * 批量删除评论
     *
     * @param ids 评论ID列表
     * @return 删除结果
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeleteComments(@RequestBody List<Long> ids) {
        return Result.ok(commentsService.batchDeleteComments(ids));
    }
}
