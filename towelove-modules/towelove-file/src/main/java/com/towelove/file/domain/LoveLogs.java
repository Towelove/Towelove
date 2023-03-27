package com.towelove.file.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.towelove.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 恋爱日志表(LoveLogs) 实体类
 *
 * @author 张锦标
 * @since 2023-03-26 20:42:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("love_logs")
public class LoveLogs extends BaseEntity {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 当前邮件选择的模板
     */
    private String description;
    /**
     * 发送人名称
     */
    private String urls;
    /**
     * 开启状态
     */
    private Integer status;
    /**
     * 备注
     */
    private Integer canSee;
    /**
     * 是否删除
     */
    @TableLogic(value = "0",delval = "1")
    private Integer deleted;

}

