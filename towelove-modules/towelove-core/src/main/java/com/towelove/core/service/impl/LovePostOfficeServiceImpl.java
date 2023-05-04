package com.towelove.core.service.impl;

import com.towelove.common.core.domain.PageParam;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.core.domain.lovepostoffice.LovePostOffice;
import com.towelove.core.mapper.LovePostOfficeMapper;
import com.towelove.core.service.LovePostOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author: 张锦标
 * @date: 2023/5/4 17:21
 * LovePostOfficeServiceImpl类
 */
@Service
public class LovePostOfficeServiceImpl implements LovePostOfficeService {
    @Autowired
    private LovePostOfficeMapper mapper;

    @Override
    public PageResult<LovePostOffice> selectPage(Integer pageNo, Integer pageSize, Long userId) {
        PageParam pageParam = new PageParam();
        pageParam.setPageNo(pageNo);
        pageParam.setPageSize(pageSize);
        LambdaQueryWrapperX<LovePostOffice> lqw
                 = new LambdaQueryWrapperX<>();
        lqw.eq(LovePostOffice::getReceiverId,userId);
        PageResult<LovePostOffice> lovePostOfficePageResult =
                mapper.selectPage(pageParam, lqw);
        return lovePostOfficePageResult;
    }

    @Override
    public void readBatch(ArrayList<Long> readList) {
        //TODO 根据id批量修改
        //可以采用sql语句拼接的方式
        mapper.readBatch(readList);
    }
}
