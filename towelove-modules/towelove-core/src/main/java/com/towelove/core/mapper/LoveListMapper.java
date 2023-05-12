package com.towelove.core.mapper;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.core.domain.lovelist.LoveList;
import com.towelove.core.domain.lovelogs.LoveLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代办列表(LoveList) 数据库访问层
 *
 * @author 张锦标
 * @since 2023-05-12 19:33:58
 */
@Mapper
public interface LoveListMapper extends BaseMapperX<LoveList> {

}

