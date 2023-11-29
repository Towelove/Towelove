package blossom.project.towelove.user.domain;

import blossom.project.towelove.framework.mysql.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_sign_in_log")
public class UserSignInLog extends BaseEntity {

    private Long id;

    @TableField("user_id")
    private Long userId;


}
