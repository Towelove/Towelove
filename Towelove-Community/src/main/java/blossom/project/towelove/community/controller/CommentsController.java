package blossom.project.towelove.community.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.entity.Comments;
import blossom.project.towelove.community.service.CommentsService;


import blossom.project.towelove.framework.log.annotation.LoveLog;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.entity.Comments;
import blossom.project.towelove.community.dto.CommentsRespDTO;
import blossom.project.towelove.community.req.CommentsCreateRequest;
import blossom.project.towelove.community.req.CommentsPageRequest;
import blossom.project.towelove.community.req.CommentsUpdateRequest;
import blossom.project.towelove.community.service.CommentsService;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {


    CommentsService commentsService;

    /**
     * 按照ID查询
     *
     * @param commentsId
     * @return
     */
    @GetMapping("")
    public Result<CommentsRespDTO> getCommentsById(@Validated @RequestParam(name = "commentsId") @NotNull(message = 
            "commentsId Can not be null") Long commentsId) {
        CommentsRespDTO result = commentsService.getCommentsById(commentsId);
        return Result.ok(result);
    }

    /**
     * 带条件分页查询
     *
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<CommentsRespDTO>> pageQueryComments(@Validated CommentsPageRequest requestParam) {
        return Result.ok(commentsService.pageQueryComments(requestParam));
    }

    /**
     * 基于ID修改
     *
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<CommentsRespDTO> updateComments(@Validated @RequestBody CommentsUpdateRequest updateRequest) {
        return Result.ok(commentsService.updateComments(updateRequest));
    }

    /**
     * 基于ID修改
     *
     * @param commentsId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deleteCommentsById(@RequestParam @Validated Long commentsId) {
        return Result.ok(commentsService.deleteCommentsById(commentsId));
    }

    /**
     * 根据ID批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeleteComments(@RequestBody List<Long> ids) {
        return Result.ok(commentsService.batchDeleteComments(ids));
    }

    /**
     * 创建
     *
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<CommentsRespDTO> createComments(@RequestBody @Valid CommentsCreateRequest createRequest) {
        return Result.ok(commentsService.createComments(createRequest));

    }

}



