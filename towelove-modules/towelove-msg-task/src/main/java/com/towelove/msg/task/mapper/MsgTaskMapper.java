package com.towelove.msg.task.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.domain.vo.MsgTaskPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
                .likeIfPresent(MsgTask::getContent, pageReqVO.getContent())
                .likeIfPresent(MsgTask::getNickname , pageReqVO.getNickname())
                .likeIfPresent(MsgTask::getSendTime,pageReqVO.getSendTime().toString())
                .likeIfPresent(MsgTask::getTitle,pageReqVO.getTitle()));
    }
    List<MsgTask> selectAfterTenMinJob(@Param("beginTime") DateTime beginTime,
                                       @Param("endTime") DateTime endTime);
}
