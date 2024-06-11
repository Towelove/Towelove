package blossom.project.towelove.community.convert;


import blossom.project.towelove.community.dto.PostsRespDTO;
import blossom.project.towelove.community.entity.Posts;
import blossom.project.towelove.community.req.PostsCreateRequest;
import blossom.project.towelove.community.req.PostsUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
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



    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "imageList", target = "imageList"),
            @Mapping(source = "interactInfo", target = "interactInfo"),
            @Mapping(source = "tagList", target = "tagList"),
            @Mapping(source = "userInfo", target = "userInfo"),
            @Mapping(source = "jsonMap", target = "jsonMap"),
    })
    Posts convert(PostsCreateRequest createRequest);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "imageList", target = "imageList"),
            @Mapping(source = "tagList", target = "tagList"),
            @Mapping(source = "userInfo", target = "userInfo"),
            @Mapping(source = "interactInfo", target = "interactInfo"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "createTime", target = "createTime"),
            @Mapping(source = "updateTime", target = "updateTime"),
            @Mapping(source = "deleted", target = "deleted"),
            @Mapping(source = "jsonMap", target = "jsonMap"),
            @Mapping(source = "pv", target = "pv")
    })
    PostsRespDTO convert(Posts posts);

    List<PostsRespDTO> convert(List<Posts> records);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "imageList", target = "imageList"),
            @Mapping(source = "tagList", target = "tagList"),
            @Mapping(source = "jsonMap", target = "jsonMap"),
            @Mapping(source = "userInfo", target = "userInfo"),
    })
    Posts convert(PostsUpdateRequest updateRequest);
}



