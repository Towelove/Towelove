package blossom.project.towelove.community.req;

import blossom.project.towelove.common.page.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsPageRequest  extends PageRequest {

    //文章id
    @NotNull(message = "帖子ID不能为空")
    private Long postId;

    // 新增子评论分页参数
    private Integer subPageNo = 1;

    private Integer subPageSize = 5;

}