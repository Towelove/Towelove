package blossom.project.towelove.loves.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.loves.service.LoveSubAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import blossom.project.towelove.common.response.love.album.LoveSubAlbumResponse;
import blossom.project.towelove.common.request.loves.album.LoveSubAlbumCreateRequest;
import blossom.project.towelove.common.request.loves.album.LoveSubAlbumPageRequest;
import blossom.project.towelove.common.request.loves.album.LoveSubAlbumUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * @author 张锦标
 * @since 2023-12-04 13:58:27
 * <p>
 * 恋爱相册子相册信息，包含某一相册的具体信息
 * 当前类提供如下功能：
 * 1：相册照片内容增删改查
 * 2：为某张具体照片创建便签
 * 3：当用户绑定情侣信息的时候默认的创建一个“日记相册”用来存放日记的照片
 * 4：照片默认按照上传日期进行排序 倒序排序
 */

@LoveLog
@RestController
@RequestMapping("/v1/love/sub/album")
public class LoveSubAlbumController {

    @Autowired
    private LoveSubAlbumService loveSubAlbumService;


    /**
     * 带条件分页查询
     *
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<LoveSubAlbumResponse>>
    pageQueryLoveSubAlbum(@Validated LoveSubAlbumPageRequest requestParam) {
        return Result.ok(loveSubAlbumService.pageQueryLoveSubAlbum(requestParam));
    }

    /**
     * 在相册中创建一个
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<LoveSubAlbumResponse>
    createLoveSubAlbum(@RequestBody
                       @Valid
                       LoveSubAlbumCreateRequest createRequest) {
        return Result.ok(loveSubAlbumService.createLoveSubAlbum(createRequest));

    }

    /**
     * 基于ID修改
     * 修改就是提供删除和添加效果了
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<LoveSubAlbumResponse>
    updateLoveSubAlbum(@Validated @RequestBody LoveSubAlbumUpdateRequest updateRequest) {
        return Result.ok(loveSubAlbumService.updateLoveSubAlbum(updateRequest));
    }




}


