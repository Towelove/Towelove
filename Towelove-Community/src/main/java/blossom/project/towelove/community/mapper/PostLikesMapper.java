package blossom.project.towelove.community.mapper;

import blossom.project.towelove.community.entity.PostLikes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:01:32
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@Mapper
public interface PostLikesMapper extends BaseMapper<PostLikes> {

    @Update("UPDATE post_likes SET status = 1, create_time = NOW() WHERE post_id = #{postId} AND user_id = #{userId}")
    void likePost(@Param("postId") Long postId, @Param("userId") Long userId);

    @Update("UPDATE post_likes SET status = 0, create_time = NOW() WHERE post_id = #{postId} AND user_id = #{userId}")
    void unlikePost(@Param("postId") Long postId, @Param("userId") Long userId);

    @Select("SELECT COUNT(*) FROM post_likes WHERE post_id = #{postId} AND user_id = #{userId} AND status = 1")
    int isPostLikedByUser(@Param("postId") Long postId, @Param("userId") Long userId);
}
