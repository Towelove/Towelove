package com.towelove.core.convert;

import com.towelove.core.domain.lovealbum.LoveAlbum;
import com.towelove.core.domain.lovealbum.LoveAlbumCreateReqVO;
import com.towelove.core.domain.lovealbum.LoveAlbumUpdateReqVO;
import com.towelove.core.domain.lovelogs.LoveLogs;
import com.towelove.core.domain.lovelogs.LoveLogsBaseVO;
import com.towelove.core.domain.lovelogs.LoveLogsCreateReqVO;
import com.towelove.core.domain.lovelogs.LoveLogsUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: 张锦标
 * @date: 2023/4/8 13:52
 * LoveLogsConvert类
 */
@Mapper
public interface LoveLogsConvert {
    LoveLogsConvert INSTANCE = Mappers.getMapper(LoveLogsConvert.class);

    LoveLogs convert(LoveLogsCreateReqVO createReqVO);
    LoveLogs convert(LoveLogsUpdateReqVO updateReqVO);
    LoveLogsBaseVO convert(LoveLogs loveLogs);
}
