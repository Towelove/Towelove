package blossom.project.towelove.loves.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * (TodoImages)表实体类
 *
 * @author makejava
 * @since 2023-11-30 17:15:39
 */
@Data
@TableName("todo_images")
public class TodoImages {
    @TableId
    private Long id;
    //任务ID
    @TableField("todo_id")
    private Long todoId;
    //图片链接
    @TableField("image_url")
    private String imageUrl;
    //删除标志
    @TableField("deleted")
    private Boolean deleted;

}

