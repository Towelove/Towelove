package blossom.project.towelove.loves.service;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.loves.album.AlbumsCreateRequest;
import blossom.project.towelove.common.request.loves.album.AlbumsPageRequest;
import blossom.project.towelove.common.response.love.album.AlbumsPageRespDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.loves.entity.Albums;
import blossom.project.towelove.common.response.love.album.AlbumsRespDTO;
import blossom.project.towelove.common.request.loves.album.AlbumsUpdateRequest;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2024/1/17 13:06
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 *
 */
public interface AlbumsService extends IService<Albums> {
    
    AlbumsRespDTO getAlbumsDetailById(Long AlbumsId);

    PageResponse<AlbumsPageRespDTO> pageQueryAlbums(AlbumsPageRequest pageRequest);

    AlbumsRespDTO updateAlbums(AlbumsUpdateRequest updateRequest);

    Boolean deleteAlbumsById(Long AlbumsId);

    Boolean batchDeleteAlbums(List<Long> ids);

    AlbumsRespDTO createAlbums(AlbumsCreateRequest createRequest);
}



