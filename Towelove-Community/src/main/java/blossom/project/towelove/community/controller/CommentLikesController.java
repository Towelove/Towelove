package blossom.project.towelove.community.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.service.CommentLikesService;


import blossom.project.towelove.framework.log.annotation.LoveLog;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.dto.CommentLikesRespDTO;
import blossom.project.towelove.community.req.CommentLikesCreateRequest;
import blossom.project.towelove.community.req.CommentLikesPageRequest;
import blossom.project.towelove.community.req.CommentLikesUpdateRequest;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;



/**
 * @author: ZhangBlossom
 * @date: 2024-06-09 11:41:54
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */

@LoveLog
@RestController
@RequestMapping("/comment/likes")
@RequiredArgsConstructor
public class CommentLikesController {


    CommentLikesService commentLikesService;

    /**
     * 按照ID查询
     *
     * @param commentLikesId
     * @return
     */
    @GetMapping("")
    public Result<CommentLikesRespDTO> getCommentLikesById(@Validated @RequestParam(name = "commentLikesId") @NotNull(message = "commentLikesId Can not be null") Long commentLikesId) {
        CommentLikesRespDTO result = commentLikesService.getCommentLikesById(commentLikesId);
        return Result.ok(result);
    }

    /**
     * 带条件分页查询
     *
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<CommentLikesRespDTO>> pageQueryCommentLikes(@Validated CommentLikesPageRequest requestParam) {
        return Result.ok(commentLikesService.pageQueryCommentLikes(requestParam));
    }

    /**
     * 基于ID修改
     *
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<CommentLikesRespDTO> updateCommentLikes(@Validated @RequestBody CommentLikesUpdateRequest updateRequest) {
        return Result.ok(commentLikesService.updateCommentLikes(updateRequest));
    }

    /**
     * 基于ID修改
     *
     * @param commentLikesId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deleteCommentLikesById(@RequestParam @Validated Long commentLikesId) {
        return Result.ok(commentLikesService.deleteCommentLikesById(commentLikesId));
    }

    /**
     * 根据ID批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeleteCommentLikes(@RequestBody List<Long> ids) {
        return Result.ok(commentLikesService.batchDeleteCommentLikes(ids));
    }

    /**
     * 创建
     *
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<CommentLikesRespDTO> createCommentLikes(@RequestBody @Valid CommentLikesCreateRequest createRequest) {
        return Result.ok(commentLikesService.createCommentLikes(createRequest));

    }

}



