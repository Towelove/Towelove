package blossom.project.towelove.community.entity;

import java.time.LocalDateTime;
import java.util.Date;

import blossom.project.towelove.framework.mysql.config.JacksonTypeHandler;
import blossom.project.towelove.framework.mysql.domain.BaseEntity;

import java.io.Serializable;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    //创建时间
    private LocalDateTime createdTime;

    //更新时间
    private LocalDateTime updatedTime;

    //用户行为状态（留用扩展）
    private Integer status;

    //删除标志（0代表未删除，1代表已删除）
    private Integer deleted;
    //备注
    private String remark;
    
    //json集合，存储额外数据
    @TableField(value = "json_map",typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> jsonMap;

}




