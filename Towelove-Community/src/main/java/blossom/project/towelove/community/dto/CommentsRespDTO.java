package blossom.project.towelove.community.dto;

import blossom.project.towelove.community.entity.inner.PictureInfo;
import blossom.project.towelove.community.entity.inner.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论响应DTO
 * 用于返回评论信息
 *
 * @autor: ZhangBlossom
 * @date: 2024-06-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsRespDTO {

    // 评论ID
    private Long id;

    // 用户ID
    private Long userId;

    // 文章ID
    private Long postId;

    // 评论用户信息
    private UserInfo userInfo;

    // 评论内容
    private String content;

    // 父评论ID
    private Long parentId;

    // 评论时间
    private LocalDateTime createTime;

    // 点赞数量
    private Integer likesNum;

    // 评论标签
    private List<String> showTags;

    // 子评论列表
    private List<CommentsRespDTO> subComments;

    // 被@的用户
    private List<UserInfo> atUsers;

    // 图片信息集合
    private List<PictureInfo> pictureInfos;

    // 当前评论是否有更多的子评论可以展开
    private Boolean subCommentHasMore;

    // 子评论数量
    private Integer subCommentCount;
}
