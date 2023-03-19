package com.towelove.task.api.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.towelove.common.core.web.domain.BaseEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:37
 * MsgTaskInfo类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "msg_task", autoResultMap = true)
public class MsgTask extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 邮箱账号id
     */
    private Long accountId;
    /**
     * 模板id
     */
    private Long templateId;
    /**
     * 接收消息的邮箱账号
     */
    private String receiveAccount;
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
     * 消息发送时间
     */
    private Date sendTime;
    /**
     * 参数数组(自动根据内容生成)
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> params;

    /**
     * 状态
     * 枚举
     */
    private Integer status;
    /**
     * 是否删除
     * 使用逻辑删除
     */
    @TableLogic
    private Boolean deleted;
}
