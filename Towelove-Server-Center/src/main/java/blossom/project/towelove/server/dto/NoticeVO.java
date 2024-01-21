package blossom.project.towelove.server.dto;

import blossom.project.towelove.framework.mysql.config.JacksonTypeHandler;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Map;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.dto
 * @className: NoticeVO
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 22:16
 * @version: 1.0
 */
@Data
public class NoticeVO {
    private String content;

    private Integer type;

    @TableField(value = "json_map",typeHandler = JacksonTypeHandler.class)
    private Map<String,Object> json_map;
}
