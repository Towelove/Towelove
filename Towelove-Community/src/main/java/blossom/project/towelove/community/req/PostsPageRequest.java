package blossom.project.towelove.community.req;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotNull;

import blossom.project.towelove.common.page.PageRequest;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 22:44:29
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostsPageRequest extends PageRequest {

    @NotNull(message = "userId不能为空")
    private Long userId;

    // 文章标题（可选）
    private String title;

    // 用户昵称（可选）
    private String nickName;

    // 内容（可选）
    private String content;

    // 标签（可选）
    private String tag;

    //排序字段
    private String sortBy = "likedCount";

    //排序方式
    private String sortOrder = "DESC";

    // 其他查询条件（可选）
    private Map<String, Object> filters;

}
