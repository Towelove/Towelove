package com.towelove.file.service.impl;


import com.towelove.common.core.constant.CaffeineCacheConstant;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.redis.service.RedisService;
import com.towelove.file.domain.LoveAlbum;
import com.towelove.file.domain.vo.LoveAlbumPageReqVO;
import com.towelove.file.mapper.LoveAlbumMapper;
import com.towelove.file.service.LoveAlbumService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.reflections.Reflections.log;

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

    @Autowired
    private RedisService redisService;

    @Override
    public List<LoveAlbum> selectList() {
        return loveAlbumMapper.selectList();
    }

    @Override
    public PageResult<LoveAlbum> selectPage(LoveAlbumPageReqVO pageReqVO) {
        return loveAlbumMapper.selectPage(pageReqVO);
    }

    @Override
    @Cacheable(cacheNames = "loveAlbum",key = "#loveAlbumId")
    public LoveAlbum selectLoveAlbumById(Long loveAlbumId) {
        String key= CaffeineCacheConstant.LOVEALBUM + loveAlbumId;
        //先查询 Redis
        Object obj = redisService.getCacheObject(key);
        if (Objects.nonNull(obj)){
            log.info("get data from redis");
            return (LoveAlbum) obj;
        }
        // Redis没有则查询 DB
        log.info("get data from database");
        LoveAlbum loveAlbum = loveAlbumMapper.selectById(loveAlbumId);
        redisService.setCacheObject(key,loveAlbum,120L, TimeUnit.SECONDS);
        return loveAlbum;
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
    @CachePut(cacheNames = "loveAlbum",key = "#loveAlbum.id")
    public boolean updateLoveAlbum(LoveAlbum loveAlbum) {
        if (Objects.isNull(loveAlbum)) {
            throw new RuntimeException("前端传来的对象为空");
        }
        int isUpdate = loveAlbumMapper.updateById(loveAlbum);
        if (isUpdate == 0) {
            throw new RuntimeException("修改任务失败");
        }
        redisService.setCacheObject(CaffeineCacheConstant.LOVEALBUM+loveAlbum.getId(),
                loveAlbum,120L,TimeUnit.SECONDS);
        return isUpdate > 0;
    }

    @Override
    @CacheEvict(cacheNames = "loveAlbum",key = "#loveAlbumIds")
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
        redisService.deleteObject(loveAlbumIds);
        return true;
    }
}

