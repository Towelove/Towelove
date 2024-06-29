package blossom.project.towelove.common.entity.inner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author: ZhangBlossom
 * @date: 2024/6/11 10:28
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PictureInfo {

    //图片高
    private Integer height;

    //图片宽
    private Integer weight;

    //图片url
    private String url;

    //扩展信息 留用
    private Map<String,Object> extra;

}
