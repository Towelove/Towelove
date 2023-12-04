package blossom.project.towelove.loves.service;


import blossom.project.towelove.common.page.PageResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.loves.entity.LoveSubAlbum;
import blossom.project.towelove.common.response.love.album.LoveSubAlbumResponse;
import blossom.project.towelove.common.request.loves.album.LoveSubAlbumCreateRequest;
import blossom.project.towelove.common.request.loves.album.LoveSubAlbumPageRequest;
import blossom.project.towelove.common.request.loves.album.LoveSubAlbumUpdateRequest;

import java.util.List;

/**
 * (LoveSubAlbum) 表服务接口
 *
 * @author 张锦标
 * @since 2023-12-04 13:58:28
 */
public interface LoveSubAlbumService extends IService<LoveSubAlbum> {
    

    PageResponse<LoveSubAlbumResponse> pageQueryLoveSubAlbum(LoveSubAlbumPageRequest requestParam);

    LoveSubAlbumResponse updateLoveSubAlbum(LoveSubAlbumUpdateRequest updateRequest);


    LoveSubAlbumResponse createLoveSubAlbum(LoveSubAlbumCreateRequest createRequest);
}

