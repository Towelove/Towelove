package blossom.project.towelove.community.service;


import blossom.project.towelove.common.page.PageResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.community.entity.PostFavorites;
import blossom.project.towelove.community.dto.PostFavoritesRespDTO;
import blossom.project.towelove.community.req.PostFavoritesCreateRequest;
import blossom.project.towelove.community.req.PostFavoritesPageRequest;
import blossom.project.towelove.community.req.PostFavoritesUpdateRequest;

import java.util.List;


/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 22:49:37
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 
 */

public interface PostFavoritesService extends IService<PostFavorites> {
    
    PostFavoritesRespDTO getPostFavoritesById(Long PostFavoritesId);
    
    PostFavoritesRespDTO getPostFavoritesDetailById(Long postFavoritesId);

    PageResponse<PostFavoritesRespDTO> pageQueryPostFavorites(PostFavoritesPageRequest requestParam);

    PostFavoritesRespDTO updatePostFavorites(PostFavoritesUpdateRequest updateRequest);

    Boolean deletePostFavoritesById(Long PostFavoritesId);

    Boolean batchDeletePostFavorites(List<Long> ids);

    PostFavoritesRespDTO createPostFavorites(PostFavoritesCreateRequest createRequest);
}



