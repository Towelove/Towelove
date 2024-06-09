package blossom.project.towelove.community.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.entity.PostTags;
import blossom.project.towelove.community.service.PostTagsService;


import blossom.project.towelove.framework.log.annotation.LoveLog;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.entity.PostTags;
import blossom.project.towelove.community.dto.PostTagsRespDTO;
import blossom.project.towelove.community.req.PostTagsCreateRequest;
import blossom.project.towelove.community.req.PostTagsPageRequest;
import blossom.project.towelove.community.req.PostTagsUpdateRequest;
import blossom.project.towelove.community.service.PostTagsService;


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
@RequestMapping("/v1/postTags")
@RequiredArgsConstructor
public class PostTagsController {


    PostTagsService postTagsService;

    /**
     * 按照ID查询
     *
     * @param postTagsId
     * @return
     */
    @GetMapping("")
    public Result<PostTagsRespDTO> getPostTagsById(@Validated @RequestParam(name = "postTagsId") @NotNull(message = 
            "postTagsId Can not be null") Long postTagsId) {
        PostTagsRespDTO result = postTagsService.getPostTagsById(postTagsId);
        return Result.ok(result);
    }

    /**
     * 带条件分页查询
     *
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<PostTagsRespDTO>> pageQueryPostTags(@Validated PostTagsPageRequest requestParam) {
        return Result.ok(postTagsService.pageQueryPostTags(requestParam));
    }

    /**
     * 基于ID修改
     *
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<PostTagsRespDTO> updatePostTags(@Validated @RequestBody PostTagsUpdateRequest updateRequest) {
        return Result.ok(postTagsService.updatePostTags(updateRequest));
    }

    /**
     * 基于ID修改
     *
     * @param postTagsId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deletePostTagsById(@RequestParam @Validated Long postTagsId) {
        return Result.ok(postTagsService.deletePostTagsById(postTagsId));
    }

    /**
     * 根据ID批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeletePostTags(@RequestBody List<Long> ids) {
        return Result.ok(postTagsService.batchDeletePostTags(ids));
    }

    /**
     * 创建
     *
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<PostTagsRespDTO> createPostTags(@RequestBody @Valid PostTagsCreateRequest createRequest) {
        return Result.ok(postTagsService.createPostTags(createRequest));

    }

}



