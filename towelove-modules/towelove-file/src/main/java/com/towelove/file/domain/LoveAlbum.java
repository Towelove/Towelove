package com.towelove.file.domain;

import com.towelove.common.core.web.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/25 21:28
 * LoveAlbum类
 * 对应恋爱相册
 * 思路来源于qq相册
 */
public class LoveAlbum extends BaseEntity {
    /**
     * id
     */
    private Long id;
    /**
     * 相册标题
     */
    private String title;
    /**
     * 相恋天数
     */
    private Integer daysInLove;
    /**
     * 点赞人数
     */
    private Integer likes;
    /**
     * 评论
     */
    private String[] remarks;
    /**
     * 观看人数
     */
    private Integer viewsNumber;
    /**
     * 是否他人可见
     */
    private Boolean canSee;
    /**
     * 恋爱日志 对应每一份小日志
     */
    private List<LoveLogs> loveLogs;
}
