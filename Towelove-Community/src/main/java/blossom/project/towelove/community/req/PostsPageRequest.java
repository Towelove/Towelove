package blossom.project.towelove.community.req;

import java.util.Date;

import java.io.Serializable;
import java.util.Map;

import blossom.project.towelove.community.enums.SortBy;
import blossom.project.towelove.community.enums.SortOrder;
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


    // 文章标题（可选）
    private String title;

    // 用户昵称（可选）
    private String nickName;

    // 内容（可选）
    private String content;

    // 标签（可选）
    private String tag;

    //排序字段
    private SortBy sortBy;

    //排序方式
    private SortOrder sortOrder;

    // 其他查询条件（可选）
    private Map<String, Object> filters;

    //排序枚举
    public enum SortBy {
        LIKED_COUNT("likedCount"),
        CREATE_TIME("createTime");

        private final String value;

        SortBy(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    //排序枚举
    public enum SortOrder {
        ASC("ASC"),
        DESC("DESC");

        private final String value;

        SortOrder(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
