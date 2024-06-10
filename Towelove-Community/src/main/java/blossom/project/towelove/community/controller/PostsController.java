package blossom.project.towelove.community.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.service.PostsService;


import blossom.project.towelove.framework.log.annotation.LoveLog;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.dto.PostsRespDTO;
import blossom.project.towelove.community.req.PostsCreateRequest;
import blossom.project.towelove.community.req.PostsPageRequest;
import blossom.project.towelove.community.req.PostsUpdateRequest;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;


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
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @PostMapping("")
    public Result<PostsRespDTO> createPosts(@RequestBody @Valid PostsCreateRequest createRequest) {
        return Result.ok(postsService.createPosts(createRequest));
    }

    @GetMapping("/{postId}")
    public Result<PostsRespDTO> getPostsDetailById(@PathVariable Long postId) {
        PostsRespDTO result = postsService.getPostsDetailById(postId);
        return Result.ok(result);
    }

    @PostMapping("/page")
    public Result<PageResponse<PostsRespDTO>> pageQueryPosts(@Validated
                                                             @RequestBody PostsPageRequest requestParam) {
        return Result.ok(postsService.pageQueryPosts(requestParam));
    }

    @PutMapping("")
    public Result<PostsRespDTO> updatePosts(@Validated @RequestBody PostsUpdateRequest updateRequest) {
        return Result.ok(postsService.updatePosts(updateRequest));
    }

    @DeleteMapping("")
    public Result<Boolean> deletePostsById(@RequestParam @Validated Long postsId) {
        return Result.ok(postsService.deletePostsById(postsId));
    }

    @DeleteMapping("/batch")
    public Result<Boolean> batchDeletePosts(@RequestBody List<Long> ids) {
        return Result.ok(postsService.batchDeletePosts(ids));
    }


}