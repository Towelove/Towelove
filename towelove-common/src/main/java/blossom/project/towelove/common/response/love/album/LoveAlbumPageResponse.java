package blossom.project.towelove.common.response.love.album;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.TreeMap;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/1 11:51
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * LoveAlbumPageResponse类
 * 分页查询返回类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoveAlbumPageResponse {

    //编号
    @TableId
    private Long id;

    //loves_id
    private Long lovesId;

    //相册标题
    private String title;

    //相册封面，如果为空，默认使用上传的第一张照片
    private String albumCoverUrl;

    //创建时间
    private Date createTime;
}
