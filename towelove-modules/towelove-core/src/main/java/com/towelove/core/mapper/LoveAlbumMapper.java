package com.towelove.core.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.core.domain.lovealbum.LoveAlbumPageReqVO;
import com.towelove.core.domain.lovealbum.LoveAlbum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Objects;

/**
 * 恋爱相册(LoveAlbum) 数据库访问层
 *
 * @author 张锦标
 * @since 2023-03-26 20:40:07
 */
@Mapper
public interface LoveAlbumMapper extends BaseMapperX<LoveAlbum> {
    /**
     * 全查询
     *
     * @return 返回所有数据
     */
    default List<LoveAlbum> selectList() {
        return selectList(new QueryWrapper<>());
    }

    /**
     * 判断当前男女是否已经创建过情侣相册
     *
     * @param boyId  男生id
     * @param girlId 女生id
     * @return 创建过返回true 否则返回false
     */
    //default boolean duplicatedCreation(Long boyId, Long girlId) {
    //    LambdaQueryWrapperX<LoveAlbum> lqw = new LambdaQueryWrapperX<>();
    //    lqw.eqIfPresent(LoveAlbum::getBoyId, boyId)
    //            .or()
    //            .eq(!Objects.isNull(girlId),LoveAlbum::getGirlId, girlId);
    //    List<LoveAlbum> loveAlbumList = selectList(lqw);
    //    return Objects.isNull(loveAlbumList);
    //}
    int duplicatedCreation(@Param("boyId") Long boyId,@Param("girlId") Long girlId);
    default PageResult<LoveAlbum> selectPage(LoveAlbumPageReqVO pageReqVO) {
        return selectPage(pageReqVO,
                new LambdaQueryWrapperX<LoveAlbum>());
    }
}

