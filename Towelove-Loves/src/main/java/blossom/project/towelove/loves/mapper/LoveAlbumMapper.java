package blossom.project.towelove.loves.mapper;

import blossom.project.towelove.common.request.loves.album.LoveAlbumPageRequest;
import blossom.project.towelove.loves.entity.LoveAlbum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (LoveAlbum) 表数据库访问层
 *
 * @author 张锦标
 * @since 2023-11-30 16:20:47
 */
@Mapper
public interface LoveAlbumMapper extends BaseMapper<LoveAlbum> {

    /**
     * 分页查询出来恋爱相册精简信息
     * @param requestParam
     * @return
     */
    List<LoveAlbum> selectLoveAlbums(LoveAlbumPageRequest requestParam);
}

