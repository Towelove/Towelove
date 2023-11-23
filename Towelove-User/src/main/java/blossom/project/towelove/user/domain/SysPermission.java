package blossom.project.towelove.user.domain;

import blossom.project.towelove.common.domain.dto.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_permission")
public class SysPermission extends BaseEntity {
    private Long id;

    @TableField("user_id")
    private Long userId;

    private String permission;
}
