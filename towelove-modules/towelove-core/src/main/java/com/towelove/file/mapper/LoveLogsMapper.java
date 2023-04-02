package com.towelove.file.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.file.domain.LoveAlbum;
import com.towelove.file.domain.LoveLogs;
import com.towelove.file.domain.vo.LoveAlbumPageReqVO;
import com.towelove.file.domain.vo.LoveLogsPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 恋爱日志表(LoveLogs) 数据库访问层
 *
 * @author 张锦标
 * @since 2023-03-26 20:40:07
 */
@Mapper
public interface LoveLogsMapper extends BaseMapperX<LoveLogs> {
    default List<LoveLogs> selectList() {
        return selectList(new QueryWrapper<>());
    }
    default PageResult<LoveLogs> selectPage(LoveLogsPageReqVO pageReqVO) {
        return selectPage(pageReqVO,
                new LambdaQueryWrapperX<LoveLogs>());
    }

}

