package com.towelove.core.service.impl;

import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.core.domain.timeline.TimeLine;
import com.towelove.core.mapper.TimeLineMapper;
import com.towelove.core.service.TimeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/5/11 20:43
 * TimeLineServiceImpl类
 */
@Service
public class TimeLineServiceImpl implements TimeLineService {
    @Autowired
    private TimeLineMapper timeLineMapper;


    @Override
    public boolean insert(TimeLine timeLine) {
        int insert = timeLineMapper.insert(timeLine);
        return insert>0;
    }

    @Override
    public boolean delete(Long id) {
        int i = timeLineMapper.deleteById(id);
        return i>0;
    }

    @Override
    public List<TimeLine> list() {
        LambdaQueryWrapperX<TimeLine> lqw = new LambdaQueryWrapperX<>();
        lqw.orderByDesc(TimeLine::getCreateTime);
        return timeLineMapper.selectList(lqw);
    }
}
