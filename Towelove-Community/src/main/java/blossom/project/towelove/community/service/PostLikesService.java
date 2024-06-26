package blossom.project.towelove.community.service;


import blossom.project.towelove.common.page.PageResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.community.entity.PostLikes;
import blossom.project.towelove.community.dto.PostLikesRespDTO;
import blossom.project.towelove.community.req.PostLikesCreateRequest;
import blossom.project.towelove.community.req.PostLikesPageRequest;
import blossom.project.towelove.community.req.PostLikesUpdateRequest;

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

public interface PostLikesService {
    void likePost(Long postId, Long userId);
    void unlikePost(Long postId, Long userId);
    boolean isPostLikedByUser(Long postId, Long userId);
}


