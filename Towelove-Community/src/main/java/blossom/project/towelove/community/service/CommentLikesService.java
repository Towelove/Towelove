package blossom.project.towelove.community.service;

import blossom.project.towelove.community.entity.CommentLikes;
import blossom.project.towelove.community.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CommentLikesService extends IService<CommentLikes> {
    void likeComment(Long commentId, Long userId);
    void unlikeComment(Long commentId, Long userId);

    boolean isCommentLiked(Long commentId, Long userId);
}
