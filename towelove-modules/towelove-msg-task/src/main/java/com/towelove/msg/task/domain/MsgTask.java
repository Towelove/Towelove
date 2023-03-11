package com.towelove.msg.task.domain;

import com.towelove.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:37
 * MsgTaskInfo类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MsgTask extends BaseEntity {
    private String username;
    private String mail;
}
