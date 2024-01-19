package blossom.project.towelove.loves.controller;


import blossom.project.towelove.common.page.PageRequest;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.loves.album.AlbumsPageRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.love.album.AlbumsPageRespDTO;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.loves.service.AlbumsService;



import org.springframework.web.bind.annotation.*;
import java.util.List;

import blossom.project.towelove.common.response.love.album.AlbumsRespDTO;
import blossom.project.towelove.common.request.loves.album.AlbumsCreateRequest;
import blossom.project.towelove.common.request.loves.album.AlbumsUpdateRequest;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


/**
 * @author: ZhangBlossom
 * @date: 2024/1/17 13:06
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 *
 */

@LoveLog
@RestController
@RequestMapping("/v1/albums")
@RequiredArgsConstructor
public class AlbumsController {
  

    private final AlbumsService albumsService;

    /**
     * 创建
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<AlbumsRespDTO> createAlbums(@RequestBody @Validated AlbumsCreateRequest createRequest){
        return Result.ok(albumsService.createAlbums(createRequest));

    }


   /**
     * 按照ID查询相册详情信息
     * @param albumsId
     * @return
     */
    @GetMapping("")
    public Result<AlbumsRespDTO> getAlbumsDetailById(@Validated @RequestParam(name = "albumsId")
                                                   @NotNull(message = "albumsId Can not be null") Long albumsId) {
        AlbumsRespDTO result = albumsService.getAlbumsDetailById(albumsId);
        return Result.ok(result);
    }

    /**
     * 分页查询 根据情侣的coupleId查询
     * @param pageRequest
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<AlbumsPageRespDTO>> pageQueryAlbums(@RequestBody @Validated AlbumsPageRequest pageRequest) {
        return Result.ok(albumsService.pageQueryAlbums(pageRequest));
    }

    /**
     * 基于ID修改
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<AlbumsRespDTO> updateAlbums(@Validated @RequestBody AlbumsUpdateRequest updateRequest){
       return Result.ok(albumsService.updateAlbums(updateRequest));
    }

    /**
     * 基于ID删除
     * @param albumsId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deleteAlbumsById(@RequestParam @Validated Long albumsId){
        return Result.ok(albumsService.deleteAlbumsById(albumsId));
    }

    /**
     * 根据ID批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeleteAlbums(@RequestBody List<Long> ids){
        return Result.ok(albumsService.batchDeleteAlbums(ids));
    }

}



