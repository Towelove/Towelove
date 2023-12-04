package blossom.project.towelove.loves.convert;

import blossom.project.towelove.common.request.loves.album.LoveAlbumCreateRequest;
import blossom.project.towelove.common.request.loves.album.LoveAlbumUpdateRequest;
import blossom.project.towelove.common.response.love.album.LoveAlbumDetailResponse;
import blossom.project.towelove.common.response.love.album.LoveAlbumPageResponse;
import blossom.project.towelove.loves.entity.LoveAlbum;
import org.aspectj.weaver.ast.Var;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/30 20:25
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * LoveAlbumConvert接口
 */
@Mapper
public interface LoveAlbumConvert {
    LoveAlbumConvert INSTANCE = Mappers.getMapper(LoveAlbumConvert.class);

    LoveAlbum convert(LoveAlbumCreateRequest createRequest);
    LoveAlbum convert(LoveAlbumUpdateRequest createRequest);

    List<LoveAlbumPageResponse> convert(List<LoveAlbum> albums);

    LoveAlbumDetailResponse convert(LoveAlbum loveAlbum);
}
