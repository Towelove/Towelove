package blossom.project.towelove.community.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.entity.PostImages;
import blossom.project.towelove.community.service.PostImagesService;


import blossom.project.towelove.framework.log.annotation.LoveLog;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.entity.PostImages;
import blossom.project.towelove.community.dto.PostImagesRespDTO;
import blossom.project.towelove.community.req.PostImagesCreateRequest;
import blossom.project.towelove.community.req.PostImagesPageRequest;
import blossom.project.towelove.community.req.PostImagesUpdateRequest;
import blossom.project.towelove.community.service.PostImagesService;


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
@RequestMapping("/v1/postImages")
@RequiredArgsConstructor
public class PostImagesController {


    PostImagesService postImagesService;

    /**
     * 按照ID查询
     *
     * @param postImagesId
     * @return
     */
    @GetMapping("")
    public Result<PostImagesRespDTO> getPostImagesById(@Validated @RequestParam(name = "postImagesId") @NotNull(message = "postImagesId Can not be null") Long postImagesId) {
        PostImagesRespDTO result = postImagesService.getPostImagesById(postImagesId);
        return Result.ok(result);
    }

    /**
     * 带条件分页查询
     *
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<PostImagesRespDTO>> pageQueryPostImages(@Validated PostImagesPageRequest requestParam) {
        return Result.ok(postImagesService.pageQueryPostImages(requestParam));
    }

    /**
     * 基于ID修改
     *
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<PostImagesRespDTO> updatePostImages(@Validated @RequestBody PostImagesUpdateRequest updateRequest) {
        return Result.ok(postImagesService.updatePostImages(updateRequest));
    }

    /**
     * 基于ID修改
     *
     * @param postImagesId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deletePostImagesById(@RequestParam @Validated Long postImagesId) {
        return Result.ok(postImagesService.deletePostImagesById(postImagesId));
    }

    /**
     * 根据ID批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeletePostImages(@RequestBody List<Long> ids) {
        return Result.ok(postImagesService.batchDeletePostImages(ids));
    }

    /**
     * 创建
     *
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<PostImagesRespDTO> createPostImages(@RequestBody @Valid PostImagesCreateRequest createRequest) {
        return Result.ok(postImagesService.createPostImages(createRequest));

    }

}



