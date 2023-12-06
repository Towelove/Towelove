package blossom.project.towelove.loves.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/4 13:32
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * LoveSubAlbumConvert接口
 */
@Mapper
public interface LoveSubAlbumConvert {
    LoveAlbumConvert INSTANCE = Mappers.getMapper(LoveAlbumConvert.class);
}
