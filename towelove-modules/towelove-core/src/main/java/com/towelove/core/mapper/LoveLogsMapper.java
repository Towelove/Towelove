package com.towelove.core.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.core.domain.lovelogs.LoveLogsPageReqVO;
import com.towelove.core.domain.lovelogs.LoveLogs;
import org.apache.ibatis.annotations.Mapper;

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
        //根据lovealbumid查询并且倒叙返回
        return selectPage(pageReqVO,
                new LambdaQueryWrapperX<LoveLogs>()
                .eq(LoveLogs::getLoveAlbumId,pageReqVO.getLoveAlbumId())
                        //.eq(LoveLogs::getDeleted,false)
                        .orderBy(true,false,LoveLogs::getCreateTime));
    }

}

