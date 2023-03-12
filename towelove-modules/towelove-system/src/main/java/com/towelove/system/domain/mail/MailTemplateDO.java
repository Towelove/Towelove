package com.towelove.system.domain.mail;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.towelove.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 邮件模版 DO
 *
 * @author: 张锦标
 * @since 2023-03-01
 */
@TableName(value = "sys_mail_template", autoResultMap = true)
@Data
public class MailTemplateDO implements Serializable {

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
     * 发送的邮箱账号编号 对应到MailAccount的id字段
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
    private static final long serialVersionUID = 1L;

    /** 搜索值 */
    @JsonIgnore
    @TableField(exist = false)
    private String searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;
    /**
     * 参数数组(自动根据内容生成)
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> params;

    @TableLogic
    private Boolean deleted;
}
