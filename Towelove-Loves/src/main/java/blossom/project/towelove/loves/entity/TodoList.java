package blossom.project.towelove.loves.entity;

import blossom.project.towelove.framework.mysql.domain.BaseEntity;
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
public class TodoList extends BaseEntity {
    @TableId
    private Long id;
    //父级id
    @TableField("parent_id")
    private Long parentId;
    //用户id  user_id  loves_id
    @TableField("couple_id")
    private Long coupleId;
    @TableField("msgTask_id")
    private Long msgTaskId;
    //标题
    private String title;
    //描述
    private String description;
    //截止日期
    private LocalDateTime deadline;
    //完成日期
    @TableField("completion_date")
    private LocalDateTime completionDate;

    /**
     * @Comment("是否提醒")
     */
    private boolean reminder;

    /**
     * @Comment("是否小組件")
     */
    private boolean widget;


    public boolean ongoing(){
        return this.getStatus() == 0 && LocalDateTime.now().isBefore(deadline) && this.getDeleted() == 0;
    }

}

