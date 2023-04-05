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
     * 恋爱相册id
     * 用于获取当前日志属于哪一个相册
     */
    private Long loveAlbumId;
    /**
     * 今日描述
     */
    private String description;
    /**
     * 照片url
     * 根据英文逗号分割
     */
    private String urls;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 外人是否可见
     */
    private Integer canSee;

}

