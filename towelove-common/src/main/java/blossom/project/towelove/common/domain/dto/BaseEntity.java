package blossom.project.towelove.common.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_by",fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField("deleted")
    @TableLogic(value = "0", delval = "1")
    private int deleted;

    @TableField(value = "remark",fill = FieldFill.INSERT)
    private String remark;

    @TableField(value = "status",fill = FieldFill.INSERT)
    private Integer status;

}
