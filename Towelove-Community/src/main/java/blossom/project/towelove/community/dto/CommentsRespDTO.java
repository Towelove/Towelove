package blossom.project.towelove.community.dto;

import lombok.Data;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;



/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:01:31
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsRespDTO {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private Long parentId;
    private LocalDateTime createTime;
    private Integer likesNum;
    private Integer pinned;
}