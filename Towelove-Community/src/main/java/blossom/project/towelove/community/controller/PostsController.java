package blossom.project.towelove.community.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.entity.Posts;
import blossom.project.towelove.community.service.PostsService;


import blossom.project.towelove.framework.log.annotation.LoveLog;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.entity.Posts;
import blossom.project.towelove.community.dto.PostsRespDTO;
import blossom.project.towelove.community.req.PostsCreateRequest;
import blossom.project.towelove.community.req.PostsPageRequest;
import blossom.project.towelove.community.req.PostsUpdateRequest;
import blossom.project.towelove.community.service.PostsService;


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
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostsController {


    PostsService postsService;

    /**
     * 按照ID查询
     *
     * @param postsId
     * @return
     */
    @GetMapping("")
    public Result<PostsRespDTO> getPostsById(@Validated @RequestParam(name = "postsId") @NotNull(message = "postsId " +
            "Can not be null") Long postsId) {
        PostsRespDTO result = postsService.getPostsById(postsId);
        return Result.ok(result);
    }

    /**
     * 带条件分页查询
     *
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<PostsRespDTO>> pageQueryPosts(@Validated PostsPageRequest requestParam) {
        return Result.ok(postsService.pageQueryPosts(requestParam));
    }

    /**
     * 基于ID修改
     *
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<PostsRespDTO> updatePosts(@Validated @RequestBody PostsUpdateRequest updateRequest) {
        return Result.ok(postsService.updatePosts(updateRequest));
    }

    /**
     * 基于ID修改
     *
     * @param postsId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deletePostsById(@RequestParam @Validated Long postsId) {
        return Result.ok(postsService.deletePostsById(postsId));
    }

    /**
     * 根据ID批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeletePosts(@RequestBody List<Long> ids) {
        return Result.ok(postsService.batchDeletePosts(ids));
    }

    /**
     * 创建
     *
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<PostsRespDTO> createPosts(@RequestBody @Valid PostsCreateRequest createRequest) {
        return Result.ok(postsService.createPosts(createRequest));

    }

}



