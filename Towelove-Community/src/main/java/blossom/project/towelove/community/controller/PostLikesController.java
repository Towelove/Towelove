package blossom.project.towelove.community.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.entity.PostLikes;
import blossom.project.towelove.community.service.PostLikesService;


import blossom.project.towelove.framework.log.annotation.LoveLog;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.entity.PostLikes;
import blossom.project.towelove.community.dto.PostLikesRespDTO;
import blossom.project.towelove.community.req.PostLikesCreateRequest;
import blossom.project.towelove.community.req.PostLikesPageRequest;
import blossom.project.towelove.community.req.PostLikesUpdateRequest;
import blossom.project.towelove.community.service.PostLikesService;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.Executors;


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
@RequestMapping("/v1/postLikes")
@RequiredArgsConstructor
public class PostLikesController {


    PostLikesService postLikesService;

    /**
     * 按照ID查询
     *
     * @param postLikesId
     * @return
     */
    @GetMapping("")
    public Result<PostLikesRespDTO> getPostLikesById(@Validated @RequestParam(name = "postLikesId") @NotNull(message 
            = "postLikesId Can not be null") Long postLikesId) {
        PostLikesRespDTO result = postLikesService.getPostLikesById(postLikesId);
        return Result.ok(result);
    }

    /**
     * 带条件分页查询
     *
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<PostLikesRespDTO>> pageQueryPostLikes(@Validated PostLikesPageRequest requestParam) {
        return Result.ok(postLikesService.pageQueryPostLikes(requestParam));
    }

    /**
     * 基于ID修改
     *
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<PostLikesRespDTO> updatePostLikes(@Validated @RequestBody PostLikesUpdateRequest updateRequest) {
        return Result.ok(postLikesService.updatePostLikes(updateRequest));
    }

    /**
     * 基于ID修改
     *
     * @param postLikesId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deletePostLikesById(@RequestParam @Validated Long postLikesId) {
        return Result.ok(postLikesService.deletePostLikesById(postLikesId));
    }

    /**
     * 根据ID批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeletePostLikes(@RequestBody List<Long> ids) {
        return Result.ok(postLikesService.batchDeletePostLikes(ids));
    }

    /**
     * 创建
     *
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<PostLikesRespDTO> createPostLikes(@RequestBody @Valid PostLikesCreateRequest createRequest) {
        return Result.ok(postLikesService.createPostLikes(createRequest));

    }

}



