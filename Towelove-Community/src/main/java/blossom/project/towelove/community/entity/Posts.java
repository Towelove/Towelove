package blossom.project.towelove.community.entity;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;

import blossom.project.towelove.framework.mysql.config.JacksonTypeHandler;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
@TableName(value = "posts", autoResultMap = true)
public class Posts {
    //主键，自增长的文章ID
    @TableId
    private Long id;

    //文章标题，长度限制为255字符
    private String title;

    //文章的文本内容
    private String content;

    //发表文章的用户ID，不能为空
    private Long userId;

    //帖子状态（待扩展）
    private Integer status;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //更新时间
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    //删除标志（0代表未删除，1代表已删除）
    @TableLogic(value = "0",delval = "1")
    private Integer deleted;

    //备注
    private String remark;

    //json集合，存储额外数据
    @TableField(value = "json_map", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> jsonMap;

    //点赞数量
    @TableField(value = "likes_num")
    private Integer likesNum;

    //收藏数量
    @TableField(value = "favorite_num")
    private Integer favoriteNum;

    //页面访问量
    @TableField(value = "pv")
    private Integer pv;

    //单一用户访问量
    @TableField(value = "uv")
    private Integer uv;

    @TableField(exist = false)
    private List<Comments> comments;

    @TableField(exist = false)
    private PostTags postTags;

    @TableField(exist = false)
    private PostImages postImages;
}




