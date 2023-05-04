package com.towelove.core.mapper;

import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.core.domain.lovepostoffice.LovePostOffice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * @author: 张锦标
 * @date: 2023/5/4 17:19
 * LovePostOfficeMapper接口
 */
@Mapper
public interface LovePostOfficeMapper extends BaseMapperX<LovePostOffice> {
    void readBatch(@Param("readList") ArrayList<Long> readList);
}
