package blossom.project.towelove.loves.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * (Todolist)表实体类
 *
 * @author makejava
 * @since 2023-11-30 17:15:38
 */
@Data
@TableName("todo_list")
public class TodoList {
    @TableId
    private Long id;
    //父级id
    @TableField("parent_id")
    private Long parentId;
    //用户id  user_id  loves_id
    @TableField("user_id")
    private Long userId;
    //标题
    private String title;
    //描述
    private String description;
    //截止日期
    private Date deadline;
    //完成状态
    @TableField("completion_status")
    private Integer completionStatus;
    //完成日期
    @TableField("completion_date")
    private Date completionDate;

    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField("deleted")
    @TableLogic(value = "0", delval = "1")
    private int deleted;

    @TableField(value = "remark", fill = FieldFill.INSERT)
    private String remark;


}

