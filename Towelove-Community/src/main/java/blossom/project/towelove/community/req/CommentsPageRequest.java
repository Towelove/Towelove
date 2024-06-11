package blossom.project.towelove.community.req;

import blossom.project.towelove.common.page.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsPageRequest  extends PageRequest {

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    //文章id
    @NotNull(message = "帖子ID不能为空")
    private Long postId;

    //当前评论的父级评论id
    private Long parentId;

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码不能小于 1")
    private Long pageNo = 1L;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 100, message = "每页条数最大值为 100")
    private Long pageSize = 5L;

    // 新增子评论分页参数
    @NotNull(message = "子评论页码不能为空")
    private Integer subPageNo = 1;

    @NotNull(message = "子评论每页数量不能为空")
    private Integer subPageSize = 5;

}