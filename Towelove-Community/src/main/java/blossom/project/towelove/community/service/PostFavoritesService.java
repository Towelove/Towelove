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

public interface PostFavoritesService {
    void favoritePost(Long postId, Long userId);
    void unfavoritePost(Long postId, Long userId);
    boolean isPostFavoritedByUser(Long postId, Long userId);
}



