package blossom.project.towelove.community.service;


import blossom.project.towelove.common.page.PageResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.community.entity.PostTags;
import blossom.project.towelove.community.dto.PostTagsRespDTO;
import blossom.project.towelove.community.req.PostTagsCreateRequest;
import blossom.project.towelove.community.req.PostTagsPageRequest;
import blossom.project.towelove.community.req.PostTagsUpdateRequest;

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

public interface PostTagsService extends IService<PostTags> {
    
    PostTagsRespDTO getPostTagsById(Long PostTagsId);
    
    PostTagsRespDTO getPostTagsDetailById(Long postTagsId);

    PageResponse<PostTagsRespDTO> pageQueryPostTags(PostTagsPageRequest requestParam);

    PostTagsRespDTO updatePostTags(PostTagsUpdateRequest updateRequest);

    Boolean deletePostTagsById(Long PostTagsId);

    Boolean batchDeletePostTags(List<Long> ids);

    PostTagsRespDTO createPostTags(PostTagsCreateRequest createRequest);
}



