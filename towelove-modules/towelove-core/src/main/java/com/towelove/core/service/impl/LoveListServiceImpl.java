package com.towelove.core.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.towelove.core.domain.lovealbum.LoveAlbum;
import com.towelove.core.domain.lovelist.LoveList;
import com.towelove.core.mapper.LoveListMapper;
import com.towelove.core.service.LoveListService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 代办列表(LoveList) 服务实现类
 *
 * @author 张锦标
 * @since 2023-05-12 19:33:57
 */
@Service
public class LoveListServiceImpl implements LoveListService {

    @Autowired
    private LoveListMapper loveListMapper;


    @Override
    public List<LoveList> selectLoveListByLoveAlbumId(Long loveAlbumId) {
        return loveListMapper.selectList(LoveList::getLoveAlbumId, loveAlbumId);
    }

    @Override
    public long insertLoveList(LoveList loveList) {
        int success = loveListMapper.insert(loveList);
        if (success > 0) {
            return loveList.getId();
        }
        throw new RuntimeException("插入LoveLogs失败");
    }

    @Override
    public boolean updateLoveList(LoveList loveList) {
        if (Objects.isNull(loveList)) {
            throw new RuntimeException("前端传来的对象为空");
        }
        int isUpdate = loveListMapper.updateById(loveList);
        if (isUpdate == 0) {
            throw new RuntimeException("修改任务失败");
        }
        return isUpdate > 0;
    }

    @Override
    public boolean deleteLoveList(Long loveListId) {

        int i = loveListMapper.deleteById(loveListId);
        if (i <= 0) {
            throw new RuntimeException("删除数据失败");
        }
        return true;
    }

    @Override
    public LoveList selectLoveListById(Long loveListId) {
        LoveList loveList = loveListMapper.selectById(loveListId);
        return loveList;
    }
}

