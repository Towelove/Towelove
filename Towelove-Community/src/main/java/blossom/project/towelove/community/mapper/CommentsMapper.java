package blossom.project.towelove.community.mapper;

import blossom.project.towelove.community.entity.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:01:31
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@Mapper
public interface CommentsMapper extends BaseMapper<Comments> {

    Page<Comments> selectPage(Page<?> page, @Param("postId") Long postId);

    List<Comments> selectSubComments(@Param("parentId") Long parentId,
                                     @Param("limit") int limit);

    List<Comments> selectSubCommentsWithLimit(@Param("parentId")Long parentId,
                                              @Param("limit")int subPageSize,
                                              @Param("offset")int offset);

    int deleteBatchIds(@Param("list") List<Long> ids);

    int countSubComments(Long parentId);
}