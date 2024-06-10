package blossom.project.towelove.community.controller;

import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.dto.CommentsRespDTO;
import blossom.project.towelove.community.req.CommentsCreateRequest;
import blossom.project.towelove.community.req.CommentsPageRequest;
import blossom.project.towelove.community.req.CommentsUpdateRequest;
import blossom.project.towelove.community.service.CommentsService;
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
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 */
@LoveLog
@RestController
@RequestMapping("/comments")
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
