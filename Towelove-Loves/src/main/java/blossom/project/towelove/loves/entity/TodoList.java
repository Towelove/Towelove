package blossom.project.towelove.loves.entity;

import java.util.Date;

import blossom.project.towelove.framework.mysql.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
    @TableField("user_id")
    private Long userId;
    //标题
    private String title;
    //描述
    private String description;
    //截止日期
    private Date deadline;
    //完成状态
    private Integer completionStatus;
    //完成日期
    private Date completionDate;

}

