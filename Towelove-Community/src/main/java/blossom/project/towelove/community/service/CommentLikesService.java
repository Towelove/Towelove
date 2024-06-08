package blossom.project.towelove.community.service;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.req.CommentLikesCreateRequest;
import blossom.project.towelove.community.req.CommentLikesPageRequest;
import blossom.project.towelove.community.req.CommentLikesUpdateRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.community.entity.CommentLikes;
import blossom.project.towelove.community.dto.CommentLikesRespDTO;

import java.util.List;


/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:01:30
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */

public interface CommentLikesService extends IService<CommentLikes> {

    CommentLikesRespDTO getCommentLikesById(Long CommentLikesId);

    CommentLikesRespDTO getCommentLikesDetailById(Long commentLikesId);

    PageResponse<CommentLikesRespDTO> pageQueryCommentLikes(CommentLikesPageRequest requestParam);

    CommentLikesRespDTO updateCommentLikes(CommentLikesUpdateRequest updateRequest);

    Boolean deleteCommentLikesById(Long CommentLikesId);

    Boolean batchDeleteCommentLikes(List<Long> ids);

    CommentLikesRespDTO createCommentLikes(CommentLikesCreateRequest createRequest);
}



