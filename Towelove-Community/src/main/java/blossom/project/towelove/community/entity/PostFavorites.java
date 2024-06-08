package blossom.project.towelove.community.entity;

import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:42:04
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
@TableName(value = "post_favorites", autoResultMap = true)
public class PostFavorites {
    //收藏ID
    @TableId
    private Long id;

    //用户ID
    private Long userId;

    //文章ID
    private Long postId;

    //收藏时间
    private LocalDateTime createTime;

    //收藏状态（1代表收藏，0代表取消收藏）
    private Integer status;

}




