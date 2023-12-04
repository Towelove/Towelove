package blossom.project.towelove.loves.entity;

import java.util.Date;

import java.io.Serializable;
import java.util.Map;

import blossom.project.towelove.framework.mysql.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * (LoveSubAlbum) 表实体类
 *
 * @author 张锦标
 * @since 2023-12-04 13:58:28
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "love_sub_album",autoResultMap = true)
public class LoveSubAlbum  extends BaseEntity {
    //编号
    @TableId
    private Long id;

    //love_album_id
    private Long loveAlbumId;

    //相册标题
    private String title;

    //照片URL：照片标签的map
    @TableField(value = "url_desc_map",typeHandler = JacksonTypeHandler.class)
    private Map<String,Memo> urlDescMap;

    //观看人数
    private Integer viewsNumber;

    //外人是否可见 0:不可见 1：可见
    private Integer canSee;

    //点赞人数
    private Integer likesNumber;
}


