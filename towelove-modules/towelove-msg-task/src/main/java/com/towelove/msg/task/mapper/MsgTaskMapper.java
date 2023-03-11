package com.towelove.msg.task.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.domain.vo.MsgTaskPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:48
 * MsgTaskMapper类
 */
@Mapper
public interface MsgTaskMapper extends BaseMapperX<MsgTask> {
    default List<MsgTask> selectList() {
        return selectList(new QueryWrapper<>());
    }

    default PageResult<MsgTask> selectPage(MsgTaskPageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<MsgTask>()
                .likeIfPresent(MsgTask::getMail, pageReqVO.getMail())
                .likeIfPresent(MsgTask::getUsername , pageReqVO.getUsername()));
    }
}
