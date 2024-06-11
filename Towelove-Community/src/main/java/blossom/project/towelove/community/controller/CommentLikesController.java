package blossom.project.towelove.community.controller;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.service.CommentLikesService;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  评论点赞
 */
@LoveLog
@RestController
@RequestMapping("/comment/likes")
public class CommentLikesController {

    @Autowired
    private CommentLikesService commentLikesService;

    /**
     * 点赞评论
     * @param commentId 评论ID
     * @param userId 用户ID
     * @return
     */
    @PostMapping("/{commentId}/{userId}/like")
    public Result<Void> likeComment(@PathVariable(value = "commentId") Long commentId,
                                    @PathVariable(value = "userId") Long userId) {
        commentLikesService.likeComment(commentId, userId);
        return Result.ok();
    }

    /**
     * 取消点赞评论
     * @param commentId 评论ID
     * @param userId 用户ID
     * @return
     */
    @PostMapping("/{commentId}/{userId}/unlike")
    public Result<Void> unlikeComment(@PathVariable(value = "commentId") Long commentId,
                                      @PathVariable(value = "userId") Long userId) {
        commentLikesService.unlikeComment(commentId, userId);
        return Result.ok();
    }

    /**
     * 用户是否点赞过评论
     * @param commentId 评论ID
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/{commentId}/{userId}/isLiked")
    public Result<Boolean> isCommentLiked(@PathVariable(value = "commentId") Long commentId,
                                          @PathVariable(value = "userId") Long userId) {
        boolean isLiked = commentLikesService.isCommentLiked(commentId, userId);
        return Result.ok(isLiked);
    }
}
