package blossom.project.towelove.community.convert;


import blossom.project.towelove.community.dto.PostImagesRespDTO;
import blossom.project.towelove.community.entity.PostImages;
import blossom.project.towelove.community.req.PostImagesCreateRequest;
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
public interface PostImagesConvert {
      PostImagesConvert INSTANCE = Mappers.getMapper(PostImagesConvert.class);

PostImages convert(PostImagesCreateRequest createRequest);
PostImagesRespDTO convert(PostImages lists);

List<PostImagesRespDTO> convert(List<PostImages> records);

  
}



