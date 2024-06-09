package blossom.project.towelove.community.dto;

import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;
import java.util.Map;

import blossom.project.towelove.framework.mysql.config.JacksonTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;


/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:01:29
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
public class PostsRespDTO {

    private Long id;

    //文章标题，长度限制为255字符
    private String title;

    //文章的文本内容
    private String content;

    //发表文章的用户ID，不能为空
    private Long userId;

}


