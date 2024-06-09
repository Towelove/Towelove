package blossom.project.towelove.community.entity;

import java.time.LocalDateTime;
import java.util.Date;

import blossom.project.towelove.framework.mysql.config.JacksonTypeHandler;
import blossom.project.towelove.framework.mysql.domain.BaseEntity;

import java.io.Serializable;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-09 00:08:01
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
@TableName(value = "user_preferences", autoResultMap = true)
public class UserPreferences extends BaseEntity {
    //用户ID
    @TableId
    private Long userId;

    //用户偏好，以JSON格式存储
    private String preferences;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    @TableField("deleted")
    @TableLogic(value = "0", delval = "1")
    private int deleted;

    @TableField(value = "remark",fill = FieldFill.INSERT)
    private String remark;

    @TableField(value = "status",fill = FieldFill.INSERT)
    private Integer status = 0;
    
    //json集合，存储额外数据
    @TableField(value = "json_map",typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> jsonMap;

}




