package blossom.project.towelove.server.entity;

import blossom.project.towelove.common.domain.dto.BaseEntity;
import blossom.project.towelove.framework.mysql.config.JacksonTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.entity.notification
 * @className: Notice
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 22:08
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("notice")
public class Notice extends BaseEntity {
    private Long id;
    @TableField("user_id")
    private Long userId;

    private String content;

    private Integer type;

    //json集合，存储额外数据
    @TableField(value = "json_map",typeHandler = JacksonTypeHandler.class)
    private Map<String,Object> jsonMap;
}
