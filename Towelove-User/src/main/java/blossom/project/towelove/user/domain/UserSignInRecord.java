package blossom.project.towelove.user.domain;

import blossom.project.towelove.framework.mysql.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("user_sign_in_record")
public class UserSignInRecord extends BaseEntity {

    @TableId(value = "id")
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("month")
    private Integer month;

    @TableField("sign_in")
    private Integer signIn;

}
