package blossom.project.towelove.user.domain;

import blossom.project.towelove.framework.mysql.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.domain
 * @className: SysUserPermission
 * @author: Link Ji
 * @description:
 * @date: 2023/11/23 23:12
 * @version: 1.0
 */
@Data
public class SysUserPermission extends BaseEntity {
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("permission_id")
    private Long permissionId;
}
