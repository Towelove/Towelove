package blossom.project.towelove.community.mapper;

import blossom.project.towelove.community.entity.CommentLikes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CommentLikesMapper extends BaseMapper<CommentLikes> {

    @Insert("INSERT INTO comment_likes (comment_id, user_id) VALUES (#{commentId}, #{userId}) ON DUPLICATE KEY UPDATE create_time = NOW()")
    void insertLike(@Param("commentId") Long commentId, @Param("userId") Long userId);

    @Delete("DELETE FROM comment_likes WHERE comment_id = #{commentId} AND user_id = #{userId}")
    void deleteLike(@Param("commentId") Long commentId, @Param("userId") Long userId);

    @Select("SELECT COUNT(*) FROM comment_likes WHERE comment_id = #{commentId} AND user_id = #{userId}")
    int isLikedByUser(@Param("commentId") Long commentId, @Param("userId") Long userId);
}
