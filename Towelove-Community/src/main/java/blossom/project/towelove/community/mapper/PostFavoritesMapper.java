package blossom.project.towelove.community.mapper;

import blossom.project.towelove.community.entity.PostFavorites;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:01:35
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@Mapper
public interface PostFavoritesMapper extends BaseMapper<PostFavorites> {

    @Update("UPDATE post_favorites SET status = 1, create_time = NOW() WHERE post_id = #{postId} AND user_id = #{userId}")
    void favoritePost(@Param("postId") Long postId, @Param("userId") Long userId);

    @Update("UPDATE post_favorites SET status = 0, create_time = NOW() WHERE post_id = #{postId} AND user_id = #{userId}")
    void unfavoritePost(@Param("postId") Long postId, @Param("userId") Long userId);

    @Select("SELECT COUNT(*) FROM post_favorites WHERE post_id = #{postId} AND user_id = #{userId} AND status = 1")
    int isPostFavoritedByUser(@Param("postId") Long postId, @Param("userId") Long userId);
}
