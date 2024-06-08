package blossom.project.towelove.community.convert;


import blossom.project.towelove.community.dto.PostTagsRespDTO;
import blossom.project.towelove.community.entity.PostTags;
import blossom.project.towelove.community.req.PostTagsCreateRequest;
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
public interface PostTagsConvert {
      PostTagsConvert INSTANCE = Mappers.getMapper(PostTagsConvert.class);

PostTags convert(PostTagsCreateRequest createRequest);
PostTagsRespDTO convert(PostTags lists);

List<PostTagsRespDTO> convert(List<PostTags> records);

  
}



