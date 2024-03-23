package blossom.project.towelove.loves.convert;

import blossom.project.towelove.common.request.loves.album.AlbumsCreateRequest;
import blossom.project.towelove.common.request.loves.album.AlbumsUpdateRequest;
import blossom.project.towelove.common.response.love.album.AlbumsPageRespDTO;
import blossom.project.towelove.common.response.love.album.AlbumsRespDTO;
import blossom.project.towelove.loves.entity.Albums;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2024/1/17 13:06
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 *
 */
@Mapper
public interface AlbumConvert {
    AlbumConvert INSTANCE = Mappers.getMapper(AlbumConvert.class);

    /**
     * 将AlbumCreateRequest转换为album
     *
     * @param createRequest
     * @return
     */
    Albums convert(AlbumsCreateRequest createRequest);

    /**
     * 将update转换为album
     * @param updateRequest
     * @return
     */
    Albums convert(AlbumsUpdateRequest updateRequest);

    /**
     * 将album转换为resp相应对象
     * @param albums
     * @return
     */
    AlbumsRespDTO convert(Albums albums);

    /**
     * 将albums集合转换为resp集合
     * @param albums
     * @return
     */
    List<AlbumsPageRespDTO> convert(List<Albums> albums);

}
