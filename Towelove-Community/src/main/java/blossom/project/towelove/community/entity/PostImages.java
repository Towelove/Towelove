package blossom.project.towelove.community.entity;


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
@TableName(value = "post_images",autoResultMap = true)
public class PostImages{
//关联的文章ID
    @TableId
    private Long postId;

    //图片的URL地址，不能为空
    private String imageUrls;

}




