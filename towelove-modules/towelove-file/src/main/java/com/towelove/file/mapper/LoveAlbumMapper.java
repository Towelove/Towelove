package com.towelove.file.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.file.domain.LoveAlbum;
import com.towelove.file.domain.vo.LoveAlbumPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * @return 返回所有数据
     */
    default List<LoveAlbum> selectList() {
        return selectList(new QueryWrapper<>());
    }
    default PageResult<LoveAlbum> selectPage(LoveAlbumPageReqVO pageReqVO) {
        return selectPage(pageReqVO,
                new LambdaQueryWrapperX<LoveAlbum>());
    }
}

