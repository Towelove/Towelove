package com.towelove.core.service.impl;


import com.towelove.common.core.constant.CaffeineCacheConstant;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.common.redis.service.RedisService;
import com.towelove.core.convert.LoveAlbumConvert;
import com.towelove.core.domain.lovealbum.*;
import com.towelove.core.service.LoveAlbumService;
import com.towelove.core.mapper.LoveAlbumMapper;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    public PageResult<LoveAlbumBaseVO> selectPage(LoveAlbumPageReqVO pageReqVO) {
        PageResult<LoveAlbumBaseVO> pageResult = new PageResult<>();
        PageResult<LoveAlbum> loveAlbumPageResult = loveAlbumMapper.selectPage(pageReqVO);
        List<LoveAlbum> list = loveAlbumPageResult.getList();

        List<LoveAlbumBaseVO> collect = list.stream().map(loveAlbum -> {
            //LoveAlbumBaseVO loveAlbumBaseVO = new LoveAlbumBaseVO();
            //BeanUtils.copyProperties(loveAlbum, loveAlbumBaseVO);
            LoveAlbumBaseVO loveAlbumBaseVO = LoveAlbumConvert.INSTANCE.convert(loveAlbum);
            return loveAlbumBaseVO;
        }).collect(Collectors.toList());
        pageResult.setList(collect);
        return pageResult;
    }

    @Override
    //@Cacheable(cacheNames = "loveAlbum", key = "#loveAlbumId")
    public LoveAlbum selectLoveAlbumById(Long loveAlbumId) {
        String key = CaffeineCacheConstant.LOVEALBUM + loveAlbumId;
        //先查询 Redis
        Object obj = redisService.getCacheObject(key);
        if (Objects.nonNull(obj)) {
            log.info("get data from redis");
            return (LoveAlbum) obj;
        }
        // Redis没有则查询 DB
        log.info("get data from database");
        LoveAlbum loveAlbum = loveAlbumMapper.selectById(loveAlbumId);
        redisService.setCacheObject(key, loveAlbum, 120L, TimeUnit.SECONDS);
        return loveAlbum;
    }

    /**
     * 根据当前id查询当前用户的伴侣的id
     * @param userId 当前用户的id
     * @return 当前用户伴侣的id -1啧表示没有伴侣
     */
    @Override
    public Long getUserIdFromLoveAlbum(Long userId) {
        //先判断恋爱相册中是否有当前用户的id
        if (loveAlbumMapper.duplicatedCreation(userId, userId) > 0) {
            //有 那么就查找她的另一半
            LambdaQueryWrapperX<LoveAlbum> lqw = new LambdaQueryWrapperX<>();
            lqw.eq(LoveAlbum::getBoyId, userId).or().eq(LoveAlbum::getGirlId, userId);
            LoveAlbum loveAlbum = loveAlbumMapper.selectOne(lqw);
            if (loveAlbum.getBoyId().equals(userId)) {
                return loveAlbum.getGirlId();
            } else {
                return loveAlbum.getBoyId();
            }
        }
        return -1L;
    }


    @Override
    public long insertLoveAlbum(LoveAlbumCreateReqVO createReqVO) {
        LoveAlbum loveAlbum = LoveAlbumConvert.INSTANCE.convert(createReqVO);
        BeanUtils.copyProperties(createReqVO, loveAlbum);

        if (loveAlbumMapper.duplicatedCreation(createReqVO.getBoyId(), createReqVO.getGirlId()) > 0) {
            throw new RuntimeException("当前男女其中一人已经创建过情侣相册，" + "不可重复创建");
        }

        int success = loveAlbumMapper.insert(loveAlbum);
        if (success > 0) {
            return loveAlbum.getId();
        }
        throw new RuntimeException("插入LoveAlbum失败");
    }

    @Override
    //@CachePut(cacheNames = "loveAlbum", key = "#updateReqVO.id")
    public boolean updateLoveAlbum(LoveAlbumUpdateReqVO updateReqVO) {
        LoveAlbum loveAlbum = LoveAlbumConvert.INSTANCE.convert(updateReqVO);
        BeanUtils.copyProperties(updateReqVO, loveAlbum);
        if (Objects.isNull(loveAlbum)) {
            throw new RuntimeException("前端传来的对象为空");
        }
        int isUpdate = loveAlbumMapper.updateById(loveAlbum);
        if (isUpdate == 0) {
            throw new RuntimeException("修改任务失败");
        }
        redisService.setCacheObject(CaffeineCacheConstant.LOVEALBUM + loveAlbum.getId(), loveAlbum, 120L,
                TimeUnit.SECONDS);
        return isUpdate > 0;
    }

    @Override
    //@CacheEvict(cacheNames = "loveAlbum", key = "#loveAlbumIds")
    public boolean deleteLoveAlbum(@RequestParam ArrayList<Long> loveAlbumIds) {
        if (Collections.isEmpty(loveAlbumIds)) {
            throw new RuntimeException("id集合为空...");
        }
        for (Long id : loveAlbumIds) {
            int i = loveAlbumMapper.deleteById(id);
            if (i <= 0) {
                throw new RuntimeException("删除数据失败");
            }
        }
        //redisService.deleteObject(loveAlbumIds);
        return true;
    }

    @Override
    public Long selectLoveAlbumIdByUserId(String userId) {
        LambdaQueryWrapperX<LoveAlbum> lqw = new LambdaQueryWrapperX<>();
        lqw.eq(LoveAlbum::getBoyId, userId).or().eq(LoveAlbum::getGirlId, userId);
        LoveAlbum loveAlbum = loveAlbumMapper.selectOne(lqw);
        return loveAlbum.getId();
    }
}

