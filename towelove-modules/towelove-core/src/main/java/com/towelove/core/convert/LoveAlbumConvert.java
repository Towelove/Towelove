package com.towelove.core.convert;

import com.towelove.common.core.domain.PageResult;
import com.towelove.core.domain.lovealbum.LoveAlbum;
import com.towelove.core.domain.lovealbum.LoveAlbumBaseVO;
import com.towelove.core.domain.lovealbum.LoveAlbumCreateReqVO;
import com.towelove.core.domain.lovealbum.LoveAlbumUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/4/8 13:52
 * LoveAlbumConvert类
 */
@Mapper
public interface LoveAlbumConvert {
    //TODO 需要进行一下test测试
    LoveAlbumConvert INSTANCE = Mappers.getMapper(LoveAlbumConvert.class);

    LoveAlbum convert(LoveAlbumCreateReqVO createReqVO);
    LoveAlbum convert(LoveAlbumUpdateReqVO updateReqVO);
    LoveAlbumBaseVO convert(LoveAlbum loveAlbum);
}
