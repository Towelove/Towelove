package blossom.project.towelove.comment.service;



import blossom.project.towelove.comment.dto.CommentsRespDTO;
import blossom.project.towelove.comment.entity.Comments;
import blossom.project.towelove.comment.req.CommentsCreateRequest;
import blossom.project.towelove.comment.req.CommentsPageRequest;
import blossom.project.towelove.common.page.PageResponse;
import com.baomidou.mybatisplus.extension.service.IService;

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

    PageResponse<CommentsRespDTO> glanceQueryComments(CommentsPageRequest requestParam);

    PageResponse<CommentsRespDTO> expandComment(Long parentId, int subPageNo, int subPageSize);
    
    PageResponse<CommentsRespDTO> pageQueryComments(CommentsPageRequest requestParam);

    Boolean deleteCommentsById(Long CommentsId);

    Boolean batchDeleteComments(List<Long> ids);

    CommentsRespDTO createComments(CommentsCreateRequest createRequest);
}



