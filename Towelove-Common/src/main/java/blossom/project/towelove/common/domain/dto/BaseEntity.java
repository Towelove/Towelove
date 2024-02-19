package blossom.project.towelove.common.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(value = "update_by",fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField("deleted")
    @TableLogic(value = "0", delval = "1")
    private int deleted;

    @TableField(value = "remark",fill = FieldFill.INSERT)
    private String remark;

    @TableField(value = "status",fill = FieldFill.INSERT)
    private Integer status;

}
