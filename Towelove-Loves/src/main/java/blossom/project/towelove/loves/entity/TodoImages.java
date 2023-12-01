package blossom.project.towelove.loves.entity;

import blossom.project.towelove.common.domain.dto.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (TodoImages)表实体类
 *
 * @author makejava
 * @since 2023-11-30 17:15:39
 */
@Data
@TableName("todo_images")
public class TodoImages {
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;
    //任务ID
    @TableField("todo_id")
    private Integer todoId;
    //图片链接
    @TableField("image_url")
    private String imageUrl;
    //删除标志
    @TableField("deleted")
    private Integer deleted;

}

