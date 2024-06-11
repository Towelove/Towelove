package blossom.project.towelove.community.mapper;

import blossom.project.towelove.community.entity.Posts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    Posts getPostsDetailById(@Param("postsId") Long postsId);

    List<Posts> pageQueryPosts(@Param("title") String title,
                               @Param("nickName") String nickName,
                               @Param("content") String content,
                               @Param("tag") String tag,
                               @Param("filters") Map<String, Object> filters,
                               @Param("pageNo") int pageNo,
                               @Param("pageSize") int pageSize,
                               @Param("sortBy") String sortBy,
                               @Param("sortOrder") String sortOrder);

    void createPosts(Posts posts);

    void updatePosts(Posts posts);

    void deletePostsById(@Param("postsId") Long postsId);

    void batchDeletePosts(@Param("list") List<Long> ids);

    void updateInteractInfo(@Param("postId") Long postId, @Param("interactInfo") String interactInfo);

}



