package com.towelove.core.service;

import com.towelove.core.domain.timeline.TimeLine;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/5/11 20:43
 * TimeLineService接口
 */
public interface TimeLineService {

    public boolean insert(TimeLine timeLine);
    public boolean delete(Long id);
    public List<TimeLine> list();
}
