package blossom.project.towelove.community.controller;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.service.PostFavoritesService;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  帖子收藏
 */
@LoveLog
@RestController
@RequestMapping("/post/favorites")
@RequiredArgsConstructor
public class PostFavoritesController {

    private final PostFavoritesService postFavoritesService;

    /**
     * 收藏帖子
     * @param postId 帖子ID
     * @param userId 用户ID
     * @return
     */
    @PostMapping("/{postId}/{userId}/favorite")
    public Result<Void> favoritePost(@PathVariable(value = "postId") Long postId,
                                     @PathVariable(value = "userId") Long userId) {
        postFavoritesService.favoritePost(postId, userId);
        return Result.ok();
    }

    /**
     * 取消收藏帖子
     * @param postId 帖子ID
     * @param userId 用户ID
     * @return
     */
    @PostMapping("/{postId}/{userId}/unfavorite")
    public Result<Void> unfavoritePost(@PathVariable(value = "postId") Long postId,
                                       @PathVariable(value = "userId") Long userId) {
        postFavoritesService.unfavoritePost(postId, userId);
        return Result.ok();
    }

    /**
     * 用户是否收藏过帖子
     * @param postId 帖子ID
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/{postId}/{userId}/isFavorited")
    public Result<Boolean> isPostFavorited(@PathVariable(value = "postId") Long postId,
                                           @PathVariable(value = "userId") Long userId) {
        boolean isFavorited = postFavoritesService.isPostFavoritedByUser(postId, userId);
        return Result.ok(isFavorited);
    }
}
