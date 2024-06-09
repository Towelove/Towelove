package blossom.project.towelove.community.mapper;

import blossom.project.towelove.community.entity.Posts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:01:29
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@Mapper
public interface PostsMapper extends BaseMapper<Posts> {

    public Posts getPostsDetailById(Long postId);
}



