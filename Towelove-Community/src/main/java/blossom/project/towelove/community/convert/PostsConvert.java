package blossom.project.towelove.community.convert;


import blossom.project.towelove.community.dto.PostsRespDTO;
import blossom.project.towelove.community.entity.posts.Posts;
import blossom.project.towelove.community.req.PostsCreateRequest;
import blossom.project.towelove.community.req.PostsUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "updateTime", target = "updateTime")
    @Mapping(source = "deleted", target = "deleted")
    @Mapping(source = "remark", target = "remark")
    @Mapping(source = "jsonMap", target = "jsonMap")
    @Mapping(source = "likesNum", target = "likesNum")
    @Mapping(source = "favoriteNum", target = "favoriteNum")
    @Mapping(source = "pv", target = "pv")
    @Mapping(source = "uv", target = "uv")
    @Mapping(source = "comments", target = "comments")
    @Mapping(source = "postTags", target = "postTags")
    @Mapping(source = "postImages", target = "postImages")
    PostsRespDTO convert(Posts posts);

    List<PostsRespDTO> convert(List<Posts> records);


    Posts convert(PostsUpdateRequest updateRequest);
}



