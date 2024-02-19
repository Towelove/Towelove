package blossom.project.towelove.loves.entity;

import blossom.project.towelove.common.domain.dto.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 *
 * 留言表实体类
 * @Author 苏佳
 * @Date 2024 01 17 18 21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "messages", autoResultMap = true)
public class Message{
    // 留言ID
    @TableId
    private Long id;

    // 伴侣ID
    private Long coupleId;

    // 创建人用户ID
    private Long userId;

    // 创建人头像URL
    private String avatarUrl;

    // 留言内容
    private String content;

    // 对话框样式标号
    private Integer dialogStyle;

    // 创建时间
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}