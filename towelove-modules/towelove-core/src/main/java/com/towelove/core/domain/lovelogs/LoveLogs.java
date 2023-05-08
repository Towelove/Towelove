package com.towelove.core.domain.lovelogs;

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
     * 恋爱日记的标题
     */
    private String title;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoveAlbumId() {
        return loveAlbumId;
    }

    public void setLoveAlbumId(Long loveAlbumId) {
        this.loveAlbumId = loveAlbumId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCanSee() {
        return canSee;
    }

    public void setCanSee(Integer canSee) {
        this.canSee = canSee;
    }
}

