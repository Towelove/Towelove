package com.towelove.core.service.impl;


import com.towelove.common.core.domain.PageResult;
import com.towelove.core.convert.LoveLogsConvert;
import com.towelove.core.domain.lovealbum.LoveAlbumBaseVO;
import com.towelove.core.domain.lovelogs.*;
import com.towelove.core.mapper.LoveLogsMapper;
import com.towelove.core.service.LoveLogsService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 恋爱日志表(LoveLogs) 服务实现类
 *
 * @author 张锦标
 * @since 2023-03-26 20:40:07
 */
@Service
public class LoveLogsServiceImpl implements LoveLogsService {

    @Autowired
    private LoveLogsMapper loveLogsMapper;


    @Override
    public List<LoveLogs> selectList() {
        return loveLogsMapper.selectList();
    }
    @Override
    public PageResult<LoveLogsBaseVO> selectPage(LoveLogsPageReqVO pageReqVO) {
        PageResult<LoveLogs> loveLogsPageResult = loveLogsMapper.selectPage(pageReqVO);
        PageResult<LoveLogsBaseVO> pageResult = new PageResult<>();
        List<LoveLogs> list = loveLogsPageResult.getList();
        List<LoveLogsBaseVO> collect = list.stream().map(loveLogs -> {
            //LoveLogsBaseVO loveLogsBaseVO = new LoveLogsBaseVO();
            //BeanUtils.copyProperties(loveLogs, loveLogsBaseVO);
            LoveLogsBaseVO loveLogsBaseVO  = LoveLogsConvert.INSTANCE.convert(loveLogs);
            return loveLogsBaseVO;
        }).collect(Collectors.toList());
        pageResult.setList(collect);
        return pageResult;
    }


    @Override
    public LoveLogs selectLoveLogsById(Long loveLogsId) {
        return loveLogsMapper.selectById(loveLogsId);
    }

    @Override
    public long insertLoveLogs(LoveLogsCreateReqVO createReqVO) {
        if (Objects.isNull(createReqVO)) {
            throw new RuntimeException("前端传来的对象为空");
        }
        LoveLogs loveLogs = LoveLogsConvert.INSTANCE.convert(createReqVO);
        int success = loveLogsMapper.insert(loveLogs);
        if (success > 0) {
            return loveLogs.getId();
        }
        throw new RuntimeException("插入LoveLogs失败");
    }

    @Override
    public boolean updateLoveLogs(LoveLogsUpdateReqVO updateReqVO) {
        if (Objects.isNull(updateReqVO)) {
            throw new RuntimeException("前端传来的对象为空");
        }
        LoveLogs loveLogs = LoveLogsConvert.INSTANCE.convert(updateReqVO);
        BeanUtils.copyProperties(updateReqVO,loveLogs);
        int isUpdate = loveLogsMapper.updateById(loveLogs);
        if (isUpdate == 0) {
            throw new RuntimeException("修改任务失败");
        }
        return isUpdate > 0;
    }

    @Override
    public boolean deleteLoveLogs(ArrayList<Long> loveLogsIds) {
        if (Collections.isEmpty(loveLogsIds)) {
            throw new RuntimeException("id集合为空...");
        }
        for (Long id : loveLogsIds) {
            int i = loveLogsMapper.deleteById(id);
            if (i <= 0) {
                throw new RuntimeException("删除数据失败");
            }
        }
        return true;
    }
}

