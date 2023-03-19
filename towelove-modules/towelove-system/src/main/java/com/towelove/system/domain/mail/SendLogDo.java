package com.towelove.system.domain.mail;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.towelove.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 季台星
 * @Date 2023 03 18 11 27
 */

@TableName("sys_send_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendLogDo extends BaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    @TableField("user_Id")
    private Long userId;

    @TableField("receive_email")
    private String receiveEmail;

    @TableField("send_email")
    private String sendEmail;

    @TableField("send_error")
    private String sendError;
    /**
     * 邮件发送状态
     */
    @TableField("sned_status")
    private Integer sendStatus;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Boolean deleted;
}
