package blossom.project.towelove.common.response.love.album;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * 相册详细信息展示类
 */
public class LoveAlbumDetailResponse {

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
    private Map<String, Object> photoDesc;

    //观看人数
    private Integer viewsNumber;

    //外人是否可见 0:不可见 1：可见
    private Integer canSee;

    //图片URL地址，至多九张照片
    private String photoUrls;

    //点赞人数
    private Integer likesNumber;

    //创建时间
    private Date createTime;


}

