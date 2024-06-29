package blossom.project.towelove.comment.req;


import blossom.project.towelove.common.entity.inner.PictureInfo;
import blossom.project.towelove.common.entity.inner.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 评论创建请求
 *
 * @autor: ZhangBlossom
 * @date: 2024-06-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsCreateRequest {

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotNull(message = "文章ID不能为空")
    private Long postId;

    @NotNull(message = "用户信息不能为空")
    private UserInfo userInfo;

    @NotBlank(message = "评论内容不能为空")
    private String content;

    private Long parentId;

    private List<String> showTags;

    private List<UserInfo> atUsers;

    private List<PictureInfo> pictureInfos;
}
