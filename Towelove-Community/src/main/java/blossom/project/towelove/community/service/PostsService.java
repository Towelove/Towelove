package blossom.project.towelove.community.service;


import blossom.project.towelove.common.page.PageResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.community.entity.Posts;
import blossom.project.towelove.community.dto.PostsRespDTO;
import blossom.project.towelove.community.req.PostsCreateRequest;
import blossom.project.towelove.community.req.PostsPageRequest;
import blossom.project.towelove.community.req.PostsUpdateRequest;

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

public interface PostsService extends IService<Posts> {
    
    PostsRespDTO getPostsDetailById(Long postsId);

    PageResponse<PostsRespDTO> pageQueryPosts(PostsPageRequest requestParam);

    PostsRespDTO updatePosts(PostsUpdateRequest updateRequest);

    Boolean deletePostsById(Long PostsId);

    Boolean batchDeletePosts(List<Long> ids);

    PostsRespDTO createPosts(PostsCreateRequest createRequest);
}



