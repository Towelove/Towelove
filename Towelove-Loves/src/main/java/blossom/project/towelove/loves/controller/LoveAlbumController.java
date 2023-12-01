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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * (LoveAlbum) 表控制层
 *
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
     * 创建
     * @param files 上传的文件
     * @param createRequest 相册信息
     * @return
     */
    @PostMapping("")
    public Result<LoveAlbumDetailResponse> createLoveAlbum(@RequestPart("files") List<MultipartFile> files,
                                                           @RequestPart("album") @Valid LoveAlbumCreateRequest createRequest) {
        return Result.ok(loveAlbumService.createLoveAlbum(files,createRequest));

    }
    /**
     * 按照ID查询
     *
     * @param loveAlbumId
     * @return
     */
    @GetMapping("")
    public Result<LoveAlbumDetailResponse> getLoveAlbumById(@Validated @RequestParam(name = "loveAlbumId") @NotNull(message = "loveAlbumId Can not be null") Long loveAlbumId) {
        LoveAlbumDetailResponse result = loveAlbumService.getLoveAlbumById(loveAlbumId);
        return Result.ok(result);
    }

    /**
     * 带条件分页查询
     *
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<LoveAlbumPageResponse>> pageQueryLoveAlbum(@Validated LoveAlbumPageRequest requestParam) {
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
     * 基于ID修改
     *
     * @param loveAlbumId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deleteLoveAlbumById(@RequestParam @Validated Long loveAlbumId) {
        return Result.ok(loveAlbumService.deleteLoveAlbumById(loveAlbumId));
    }

    /**
     * 根据ID批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeleteLoveAlbum(@RequestBody List<Long> ids) {
        return Result.ok(loveAlbumService.batchDeleteLoveAlbum(ids));
    }


}


