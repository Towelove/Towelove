package blossom.project.towelove.loves.service;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.response.love.album.LoveAlbumDetailResponse;
import blossom.project.towelove.common.response.love.album.LoveAlbumPageResponse;
import blossom.project.towelove.loves.entity.LoveAlbum;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.common.request.loves.album.LoveAlbumCreateRequest;
import blossom.project.towelove.common.request.loves.album.LoveAlbumPageRequest;
import blossom.project.towelove.common.request.loves.album.LoveAlbumUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (LoveAlbum) 表服务接口
 *
 * @author 张锦标
 * @since 2023-11-30 16:20:45
 */
public interface LoveAlbumService extends IService<LoveAlbum> {

    LoveAlbumDetailResponse getLoveAlbumById(Long LoveAlbumId);

    PageResponse<LoveAlbumPageResponse> pageQueryLoveAlbum(LoveAlbumPageRequest requestParam);

    LoveAlbumDetailResponse updateLoveAlbum(LoveAlbumUpdateRequest updateRequest);

    Boolean deleteLoveAlbumById(Long LoveAlbumId);

    Boolean batchDeleteLoveAlbum(List<Long> ids);

    LoveAlbumDetailResponse createLoveAlbum(List<MultipartFile> files,
                                            LoveAlbumCreateRequest createRequest);

    void deleteImageFromAlbum(Long id, Integer imageIndex);
}