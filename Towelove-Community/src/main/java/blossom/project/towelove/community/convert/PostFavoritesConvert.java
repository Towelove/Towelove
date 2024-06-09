package blossom.project.towelove.community.convert;


import blossom.project.towelove.community.dto.PostFavoritesRespDTO;
import blossom.project.towelove.community.entity.PostFavorites;
import blossom.project.towelove.community.req.PostFavoritesCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:49:38
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 
 */

@Mapper
public interface PostFavoritesConvert {
      PostFavoritesConvert INSTANCE = Mappers.getMapper(PostFavoritesConvert.class);

PostFavorites convert(PostFavoritesCreateRequest createRequest);
PostFavoritesRespDTO convert(PostFavorites lists);

List<PostFavoritesRespDTO> convert(List<PostFavorites> records);

  
}



