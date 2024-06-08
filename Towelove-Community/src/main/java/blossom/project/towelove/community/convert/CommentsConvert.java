package blossom.project.towelove.community.convert;


import blossom.project.towelove.community.dto.CommentsRespDTO;
import blossom.project.towelove.community.entity.Comments;
import blossom.project.towelove.community.req.CommentsCreateRequest;
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
public interface CommentsConvert {
    CommentsConvert INSTANCE = Mappers.getMapper(CommentsConvert.class);

    Comments convert(CommentsCreateRequest createRequest);

    CommentsRespDTO convert(Comments lists);

    List<CommentsRespDTO> convert(List<Comments> records);


}



