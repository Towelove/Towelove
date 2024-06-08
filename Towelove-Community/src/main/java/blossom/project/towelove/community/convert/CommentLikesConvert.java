package blossom.project.towelove.community.convert;


import blossom.project.towelove.community.dto.CommentLikesRespDTO;
import blossom.project.towelove.community.entity.CommentLikes;
import blossom.project.towelove.community.req.CommentLikesCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 22:47:19
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */

@Mapper
public interface CommentLikesConvert {
    CommentLikesConvert INSTANCE = Mappers.getMapper(CommentLikesConvert.class);

    CommentLikes convert(CommentLikesCreateRequest createRequest);

    CommentLikesRespDTO convert(CommentLikes lists);

    List<CommentLikesRespDTO> convert(List<CommentLikes> records);


}



