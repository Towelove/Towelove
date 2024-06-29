package blossom.project.towelove.comment.service;


import blossom.project.towelove.comment.entity.CommentLikes;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CommentLikesService extends IService<CommentLikes> {
    void likeComment(Long commentId, Long userId);
    void unlikeComment(Long commentId, Long userId);

    boolean isCommentLiked(Long commentId, Long userId);
}
