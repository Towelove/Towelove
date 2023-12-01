package blossom.project.towelove.loves.entity;

import java.util.Date;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import blossom.project.towelove.common.domain.dto.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * (LoveAlbum) 表实体类
 *
 * @author 张锦标
 * @since 2023-11-30 16:22:27
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "love_album",autoResultMap = true)
public class LoveAlbum extends BaseEntity {
    //编号
    @TableId
    private Long id;

    //loves_id
    private Long lovesId;

    //相册标题
    private String title;

    //相册封面，如果为空，默认使用上传的第一张照片
    private String albumCoverUrl;

    //存储map，其中key为photo的index，value为url
    @TableField(value = "photo_desc",typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> photoDesc;

    //观看人数
    private Integer viewsNumber;
    //外人是否可见 0:不可见 1：可见
    private Integer canSee;
    //图片URL地址，至多九张照片
    private String photoUrls;
    //点赞人数
    private Integer likesNumber;

}



