package blossom.project.towelove.community.convert;


import blossom.project.towelove.community.dto.PostsRespDTO;
import blossom.project.towelove.community.entity.Posts;
import blossom.project.towelove.community.req.PostsCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:49:39
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 
 */

@Mapper
public interface PostsConvert {
      PostsConvert INSTANCE = Mappers.getMapper(PostsConvert.class);

Posts convert(PostsCreateRequest createRequest);
PostsRespDTO convert(Posts lists);

List<PostsRespDTO> convert(List<Posts> records);

  
}



