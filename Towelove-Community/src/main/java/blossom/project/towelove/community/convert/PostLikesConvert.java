package blossom.project.towelove.community.convert;


import blossom.project.towelove.community.dto.PostLikesRespDTO;
import blossom.project.towelove.community.entity.PostLikes;
import blossom.project.towelove.community.req.PostLikesCreateRequest;
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
public interface PostLikesConvert {
      PostLikesConvert INSTANCE = Mappers.getMapper(PostLikesConvert.class);

PostLikes convert(PostLikesCreateRequest createRequest);
PostLikesRespDTO convert(PostLikes lists);

List<PostLikesRespDTO> convert(List<PostLikes> records);

  
}



