package blossom.project.towelove.community.service;


import blossom.project.towelove.common.page.PageResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.community.entity.PostImages;
import blossom.project.towelove.community.dto.PostImagesRespDTO;
import blossom.project.towelove.community.req.PostImagesCreateRequest;
import blossom.project.towelove.community.req.PostImagesPageRequest;
import blossom.project.towelove.community.req.PostImagesUpdateRequest;

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

public interface PostImagesService extends IService<PostImages> {
    
    PostImagesRespDTO getPostImagesById(Long PostImagesId);
    
    PostImagesRespDTO getPostImagesDetailById(Long postImagesId);

    PageResponse<PostImagesRespDTO> pageQueryPostImages(PostImagesPageRequest requestParam);

    PostImagesRespDTO updatePostImages(PostImagesUpdateRequest updateRequest);

    Boolean deletePostImagesById(Long PostImagesId);

    Boolean batchDeletePostImages(List<Long> ids);

    PostImagesRespDTO createPostImages(PostImagesCreateRequest createRequest);
}



