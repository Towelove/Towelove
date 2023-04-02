package com.towelove.file.service.impl;


import com.towelove.common.core.domain.PageResult;
import com.towelove.file.domain.LoveAlbum;
import com.towelove.file.domain.vo.LoveAlbumPageReqVO;
import com.towelove.file.mapper.LoveAlbumMapper;
import com.towelove.file.service.LoveAlbumService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 恋爱相册(LoveAlbum) 服务实现类
 *
 * @author 张锦标
 * @since 2023-03-26 20:40:06
 */
@Service
@Transactional
public class LoveAlbumServiceImpl implements LoveAlbumService {

    @Autowired
    private LoveAlbumMapper loveAlbumMapper;


    @Override
    public List<LoveAlbum> selectList() {
        return loveAlbumMapper.selectList();
    }

    @Override
    public PageResult<LoveAlbum> selectPage(LoveAlbumPageReqVO pageReqVO) {
        return loveAlbumMapper.selectPage(pageReqVO);
    }

    @Override
    public LoveAlbum selectLoveAlbumById(Long loveAlbumId) {
        return loveAlbumMapper.selectById(loveAlbumId);
    }

    @Override
    public long insertLoveAlbum(LoveAlbum loveAlbum) {
        int success = loveAlbumMapper.insert(loveAlbum);
        if (success > 0) {
            return loveAlbum.getId();
        }
        throw new RuntimeException("插入LoveAlbum失败");
    }

    @Override
    public boolean updateLoveAlbum(LoveAlbum loveAlbum) {
        if (Objects.isNull(loveAlbum)) {
            throw new RuntimeException("前端传来的对象为空");
        }
        int isUpdate = loveAlbumMapper.updateById(loveAlbum);
        if (isUpdate == 0) {
            throw new RuntimeException("修改任务失败");
        }
        return isUpdate > 0;
    }

    @Override
    public boolean deleteLoveAlbum(ArrayList<Long> loveAlbumIds) {
        if (Collections.isEmpty(loveAlbumIds)) {
            throw new RuntimeException("id集合为空...");
        }
        for (Long id : loveAlbumIds) {
            int i = loveAlbumMapper.deleteById(id);
            if (i <= 0) {
                throw new RuntimeException("删除数据失败");
            }
        }
        return true;
    }
}

