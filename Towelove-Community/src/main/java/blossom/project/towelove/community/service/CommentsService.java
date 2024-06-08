package blossom.project.towelove.community.service;


import blossom.project.towelove.common.page.PageResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.community.entity.Comments;
import blossom.project.towelove.community.dto.CommentsRespDTO;
import blossom.project.towelove.community.req.CommentsCreateRequest;
import blossom.project.towelove.community.req.CommentsPageRequest;
import blossom.project.towelove.community.req.CommentsUpdateRequest;

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

public interface CommentsService extends IService<Comments> {
    
    CommentsRespDTO getCommentsById(Long CommentsId);
    
    CommentsRespDTO getCommentsDetailById(Long commentsId);

    PageResponse<CommentsRespDTO> pageQueryComments(CommentsPageRequest requestParam);

    CommentsRespDTO updateComments(CommentsUpdateRequest updateRequest);

    Boolean deleteCommentsById(Long CommentsId);

    Boolean batchDeleteComments(List<Long> ids);

    CommentsRespDTO createComments(CommentsCreateRequest createRequest);
}



