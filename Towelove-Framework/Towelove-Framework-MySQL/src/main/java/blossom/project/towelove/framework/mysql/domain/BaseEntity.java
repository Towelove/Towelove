package blossom.project.towelove.framework.mysql.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class BaseEntity {

    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private String updateBy;

    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField("deleted")
    @TableLogic(value = "0", delval = "1")
    private int deleted;

    @TableField("remark")
    private String remark;

    @TableField("status")
    private Integer status;

    /**
     * 参数数组(自动根据内容生成)
     */
    @TableField(value = "params",typeHandler = JacksonTypeHandler.class)
    private List<String> params;

    @TableField(value = "json_map",typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> jsonMap;
}
