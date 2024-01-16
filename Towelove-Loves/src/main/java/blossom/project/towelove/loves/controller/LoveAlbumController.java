package blossom.project.towelove.loves.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.loves.album.LoveAlbumPageRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.love.album.LoveAlbumDetailResponse;
import blossom.project.towelove.common.response.love.album.LoveAlbumPageResponse;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.loves.service.LoveAlbumService;


import org.springframework.web.bind.annotation.*;

import java.util.List;

import blossom.project.towelove.common.request.loves.album.LoveAlbumCreateRequest;
import blossom.project.towelove.common.request.loves.album.LoveAlbumUpdateRequest;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * 恋爱相册外层控制层
 * 提供如下功能：
 * 1：相册增删改查
 * 2：相册封面信息操作
 * 2.1：相册封面添加
 * 2.2：相册封面删除
 * 3：默认相册创建
 * 3.1：当用户没有选择和指定任何一个相册的时候，
 * 按照当天的日期创建一个默认相册给当前用
 * 3.2：日记相册创建（这个可以默认存在）
 * @author 张锦标
 * @since 2023-11-30 16:20:44
 */

@LoveLog
@RestController
@RequestMapping("/v1/love/album")
@RequiredArgsConstructor
public class LoveAlbumController {

    private final LoveAlbumService loveAlbumService;

    /**
     * 创建相册
     *
     * @param createRequest 相册初始化创建信息
     * @return
     */
    @PostMapping("")
    public Result<Long> createLoveAlbum(@RequestBody @Valid
                                        LoveAlbumCreateRequest createRequest) {
        return Result.ok(loveAlbumService.createLoveAlbum(createRequest));

    }


    /**
     * 带条件分页查询
     *
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<LoveAlbumPageResponse>>
    pageQueryLoveAlbum(@Validated LoveAlbumPageRequest requestParam) {
        return Result.ok(loveAlbumService.pageQueryLoveAlbum(requestParam));
    }

    /**
     * 基于ID修改
     *
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<LoveAlbumDetailResponse> updateLoveAlbum(@Validated @RequestBody LoveAlbumUpdateRequest updateRequest) {
        return Result.ok(loveAlbumService.updateLoveAlbum(updateRequest));
    }

    /**
     * 基于ID删除
     *
     * @param loveAlbumId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deleteLoveAlbumById(@RequestParam @Validated Long loveAlbumId) {
        return Result.ok(loveAlbumService.deleteLoveAlbumById(loveAlbumId));
    }


}


