package blossom.project.towelove.common.entity.inner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:42:04
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 文章交互信息
 */

@Data
@AllArgsConstructor
@Builder
public class InteractInfo {
    // 收藏次数
    private long collectedCount;

    // 评论次数
    private long commentCount;

    // 分享次数
    private long shareCount;

    // 是否关注
    private boolean followed;

    // 关系状态
    private String relation;

    // 是否点赞
    private boolean liked;

    // 点赞次数
    private long likedCount;

    // 是否收藏
    private boolean collected;

    // 默认构造函数
    public InteractInfo() {
        this.likedCount = 0;
        this.commentCount = 0;
        this.collectedCount = 0;
        this.followed = false;
        this.relation = "none";
        this.liked = false;
    }
}
