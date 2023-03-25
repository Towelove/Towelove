package com.towelove.file.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.towelove.common.core.web.domain.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/3/25 21:33
 * LoveLogs类
 */
//@TableName(value = "sys_mail_account", autoResultMap = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoveLogs extends BaseEntity {
    /**
     * 恋爱日志id
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date date;
    /**
     * 当天描述
     */
    private String description;
    /**
     * 照片urls
     */
    private String[] urls;
}
