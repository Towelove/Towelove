package com.towelove.system.domain.mail;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.towelove.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 邮件模版 DO
 *
 * @author wangjingyi
 * @since 2022-03-21
 */
@TableName(value = "system_mail_template", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class MailTemplateDO extends BaseEntity {

    /**
     * 主键
     */
    private Long id;
    /**
     * 模版名称
     */
    private String name;
    /**
     * 模版编号
     */
    private String code;
    /**
     * 发送的邮箱账号编号
     *
     * 关联 {@link MailAccountDO#getId()}
     */
    private Long accountId;

    /**
     * 发送人名称
     */
    private String nickname;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     *
     * 枚举
     */
    private Integer status;


}
