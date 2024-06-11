package blossom.project.towelove.community.convert;

import blossom.project.towelove.community.dto.CommentsRespDTO;
import blossom.project.towelove.community.entity.Comments;
import blossom.project.towelove.community.req.CommentsCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2024/6/10 19:47
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@Mapper
public interface CommentsConvert {
    CommentsConvert INSTANCE = Mappers.getMapper(CommentsConvert.class);

    @Mappings({
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "postId", target = "postId"),
            @Mapping(source = "userInfo", target = "userInfo"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "parentId", target = "parentId"),
            @Mapping(source = "showTags", target = "showTags")
    })
    Comments convert(CommentsCreateRequest createRequest);


    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "postId", target = "postId"),
            @Mapping(source = "userInfo", target = "userInfo"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "parentId", target = "parentId"),
            @Mapping(source = "createTime", target = "createTime"),
            @Mapping(source = "likesNum", target = "likesNum"),
            @Mapping(source = "showTags", target = "showTags"),
            @Mapping(source = "subComments", target = "subComments")
    })
    CommentsRespDTO convert(Comments comment);

    List<CommentsRespDTO> convert(List<Comments> records);
}