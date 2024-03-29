package blossom.project.towelove.loves.entity;

import blossom.project.towelove.framework.mysql.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
/**
 * @author: ZhangBlossom
 * @date: 2023/3/23 11:09
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * TodoList
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("todo_list")
public class TodoList extends BaseEntity {
    @TableId
    private Long id;

    //用户id  user_id  loves_id
    @TableField("couple_id")
    private Long coupleId;

    @TableField("msg_task_id")
    private Long msgTaskId;

    //标题
    private String title;

    //描述
    private String description;

    //截止日期
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deadline;

    //完成日期
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completionDate;

    /**
     * @Comment("是否提醒")
     */
    private boolean reminder = false;

    /**
     * @Comment("是否小組件")
     */
    private boolean widget = false;
}

