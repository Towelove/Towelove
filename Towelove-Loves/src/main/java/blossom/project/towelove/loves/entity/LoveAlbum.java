package blossom.project.towelove.loves.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("love_album")
public class LoveAlbum {
    //编号
    @TableId
    private Long id;

    //loves_id
    private Long lovesId;

    //相册标题
    private String title;
    //存储map，其中key为photo的url，value为desc
    private String photoDesc;
    //相册封面，如果为空，默认使用上传的第一张照片
    private String albumCoverUrl;
    //观看人数
    private Integer viewsNumber;
    //外人是否可见 0:不可见 1：可见
    private Integer canSee;
    //图片URL地址，至多九张照片
    private String photoUrls;
    //点赞人数
    private Integer likesNumber;
    //开启状态
    private Integer status;
    //创建时间
    private Date createTime;
    //创建者
    private String createBy;
    //更新时间
    private Date updateTime;
    //更新者
    private String updateBy;
    //是否删除
    private Integer deleted;
    //备注
    private String remark;

}



