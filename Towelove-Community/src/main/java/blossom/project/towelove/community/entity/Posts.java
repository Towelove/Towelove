package blossom.project.towelove.community.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import blossom.project.towelove.community.entity.inner.InteractInfo;
import blossom.project.towelove.community.entity.inner.UserInfo;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:42:04
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 社区文章表
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "posts", autoResultMap = true)
public class Posts {

    // 主键，自增长的文章ID
    @TableId
    private Long id;

    // 文章标题，长度限制为255字符
    private String title;

    // 文章的文本内容
    private String content;

    // 文章图片信息，存储为JSON
    @TableField(value = "image_list", typeHandler = JacksonTypeHandler.class)
    private List<String> imageList;

    // 文章标签信息，存储为JSON
    @TableField(value = "tag_list", typeHandler = JacksonTypeHandler.class)
    private List<String> tagList;

    // 用户信息，存储为JSON
    @TableField(value = "user_info", typeHandler = JacksonTypeHandler.class)
    private UserInfo userInfo;

    // 用户交互信息，存储为JSON
    @TableField(value = "interact_info", typeHandler = JacksonTypeHandler.class)
    private InteractInfo interactInfo;

    // 帖子状态（0: 私密，1: 公开）
    private Integer status;

    // 创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    // 删除标志（0代表未删除，1代表已删除）
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;


    // json集合，作为扩展字段，应对后续可能出现的变动
    @TableField(value = "json_map", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> jsonMap;

    // 页面访问量
    private Integer pv;
}

