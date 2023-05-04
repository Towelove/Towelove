package com.towelove.core.service;

import com.towelove.common.core.domain.PageResult;
import com.towelove.core.domain.lovepostoffice.LovePostOffice;

import java.util.ArrayList;

/**
 * @author: 张锦标
 * @date: 2023/5/4 17:20
 * LovePostOfficeService接口
 */
public interface LovePostOfficeService {
    PageResult<LovePostOffice> selectPage(Integer pageNo, Integer pageSize, Long userId);

    void readBatch(ArrayList<Long> readList);
}
