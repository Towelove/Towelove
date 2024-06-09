package blossom.project.towelove.community.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.entity.PostFavorites;
import blossom.project.towelove.community.service.PostFavoritesService;


import blossom.project.towelove.framework.log.annotation.LoveLog;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.entity.PostFavorites;
import blossom.project.towelove.community.dto.PostFavoritesRespDTO;
import blossom.project.towelove.community.req.PostFavoritesCreateRequest;
import blossom.project.towelove.community.req.PostFavoritesPageRequest;
import blossom.project.towelove.community.req.PostFavoritesUpdateRequest;
import blossom.project.towelove.community.service.PostFavoritesService;


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
@RequestMapping("/post/favorites")
@RequiredArgsConstructor
public class PostFavoritesController {


    PostFavoritesService postFavoritesService;

    /**
     * 按照ID查询
     *
     * @param postFavoritesId
     * @return
     */
    @GetMapping("")
    public Result<PostFavoritesRespDTO> getPostFavoritesById(@Validated @RequestParam(name = "postFavoritesId") @NotNull(message = "postFavoritesId Can not be null") Long postFavoritesId) {
        PostFavoritesRespDTO result = postFavoritesService.getPostFavoritesById(postFavoritesId);
        return Result.ok(result);
    }

    /**
     * 带条件分页查询
     *
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<PostFavoritesRespDTO>> pageQueryPostFavorites(@Validated PostFavoritesPageRequest requestParam) {
        return Result.ok(postFavoritesService.pageQueryPostFavorites(requestParam));
    }

    /**
     * 基于ID修改
     *
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<PostFavoritesRespDTO> updatePostFavorites(@Validated @RequestBody PostFavoritesUpdateRequest updateRequest) {
        return Result.ok(postFavoritesService.updatePostFavorites(updateRequest));
    }

    /**
     * 基于ID修改
     *
     * @param postFavoritesId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deletePostFavoritesById(@RequestParam @Validated Long postFavoritesId) {
        return Result.ok(postFavoritesService.deletePostFavoritesById(postFavoritesId));
    }

    /**
     * 根据ID批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeletePostFavorites(@RequestBody List<Long> ids) {
        return Result.ok(postFavoritesService.batchDeletePostFavorites(ids));
    }

    /**
     * 创建
     *
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<PostFavoritesRespDTO> createPostFavorites(@RequestBody @Valid PostFavoritesCreateRequest createRequest) {
        return Result.ok(postFavoritesService.createPostFavorites(createRequest));

    }

}



