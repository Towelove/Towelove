package blossom.project.towelove.community.controller;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.entity.PostLikes;
import blossom.project.towelove.community.entity.UserBehaviors;
import blossom.project.towelove.community.mapper.UserBehaviorsMapper;
import blossom.project.towelove.community.service.PostLikesService;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  帖子点赞
 */
@LoveLog
@RestController
@RequestMapping("/post/likes")
@RequiredArgsConstructor
public class PostLikesController {

    private final PostLikesService postLikesService;


    /**
     * 点赞帖子
     * @param postId 帖子ID
     * @param userId 用户ID
     * @return
     */
    @PostMapping("/{postId}/{userId}/like")
    public Result<Void> likePost(@PathVariable(value = "postId") Long postId,
                                 @PathVariable(value = "userId") Long userId) {
        postLikesService.likePost(postId, userId);
        return Result.ok();
    }


    /**
     * 取消点赞帖子
     * @param postId 帖子ID
     * @param userId 用户ID
     * @return
     */
    @PostMapping("/{postId}/{userId}/unlike")
    public Result<Void> unlikePost(@PathVariable(value = "postId") Long postId,
                                   @PathVariable(value = "userId") Long userId) {
        postLikesService.unlikePost(postId, userId);
        return Result.ok();
    }

    /**
     * 用户是否点赞过帖子
     * @param postId 帖子ID
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/{postId}/{userId}/isLiked")
    public Result<Boolean> isPostLiked(@PathVariable(value = "postId") Long postId,
                                       @PathVariable(value = "userId") Long userId) {
        boolean isLiked = postLikesService.isPostLikedByUser(postId, userId);
        return Result.ok(isLiked);
    }
}
