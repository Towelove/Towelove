package blossom.project.towelove.loves.service.impl;

import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.loves.diary.*;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.love.diary.*;
import blossom.project.towelove.framework.mysql.config.JacksonTypeHandler;
import blossom.project.towelove.framework.user.core.UserInfoContextHolder;
import blossom.project.towelove.loves.convert.DiaryCollectionConvert;
import blossom.project.towelove.loves.convert.DiaryConvert;
import blossom.project.towelove.loves.entity.LoveDiary;
import blossom.project.towelove.loves.entity.LoveDiaryCollection;
import blossom.project.towelove.loves.mapper.DiariesMapper;
import blossom.project.towelove.loves.mapper.LoveDiaryMapper;
import blossom.project.towelove.loves.service.DiariesService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.loves.service.Impl
 * @className: DiariesServiceImpl
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 15:54
 * @version: 1.0
 */
@Service
@RequiredArgsConstructor
public class DiariesServiceImpl extends ServiceImpl<DiariesMapper, LoveDiaryCollection> implements DiariesService {

    private final DiariesMapper diariesMapper;

    private final LoveDiaryMapper loveDiaryMapper;



    private final Logger log = LoggerFactory.getLogger(DiariesService.class);


    public final String QUICK_WRITE_DIARY_TITLE = "小记一下";
    public final String QUICK_WRITE_DIARY_COVER = "https://oss.towelove.cn/towelove-images/2024/03/12/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20240308182109_20240312132621A044.jpg";

//    private final DiaryService diaryService;

    @Override
    public List<DiaryCollectionDTO> getDiaryCollectionById() {
        Long userId = UserInfoContextHolder.getUserId();
        LambdaQueryWrapper<LoveDiaryCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(userId), LoveDiaryCollection::getUserId, userId);
        queryWrapper.orderByAsc(LoveDiaryCollection::getCreateTime);
        List<LoveDiaryCollection> loveDiaryCollections = diariesMapper.selectList(queryWrapper);
        return DiaryCollectionConvert.INSTANCE.convert(loveDiaryCollections);
    }

    @Override
    public PageResponse<DiaryCollectionDTO> getDiaryCollectionByPage(DiaryCollectionPageRequest request) {
        Long userId = UserInfoContextHolder.getUserId();
        LambdaQueryWrapper<LoveDiaryCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(userId), LoveDiaryCollection::getUserId, userId);
        queryWrapper.orderByAsc(LoveDiaryCollection::getCreateBy);
        Page<LoveDiaryCollection> page = new Page<>(request.getPageNo() - 1, request.getPageSize());
        page = diariesMapper.selectPage(page, queryWrapper);
        List<DiaryCollectionDTO> result = DiaryCollectionConvert.INSTANCE.convert(page.getRecords());
        return new PageResponse<>(page.getCurrent() //页码
                , page.getSize()
                , page.getTotal()//数量
                , result); //结果集
    }

    @Override
    public DiaryCollectionDTO createDiaryCollection(DiaryCollectionCreateRequest request) {
        if (StrUtil.isBlank(request.getTitle())) {
            request.setTitle(DateUtil.format(Date.from(Instant.now()), "yyyy-MM-dd HH:mm:ss"));
        }
        LoveDiaryCollection loveDiaryCollection = DiaryCollectionConvert.INSTANCE.convert(request);
        if (Objects.isNull(loveDiaryCollection)) {
            throw new ServiceException("入参有误");
        }
        Long userId = UserInfoContextHolder.getUserId();
        Long coupleId = UserInfoContextHolder.getCoupleId();
        loveDiaryCollection.setUserId(userId);
        loveDiaryCollection.setCoupleId(coupleId);
        try {
            diariesMapper.insert(loveDiaryCollection);
        } catch (Exception e) {
            throw new ServiceException("创建日记失败");
        }
        return DiaryCollectionConvert.INSTANCE.convert(loveDiaryCollection);
    }

    @Override
    public Boolean deleteById(Long id) {
        int loop;
        try {
            loop = diariesMapper.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("删除日机册失败");
        }
        return loop > 0;
    }

    @Override
    public List<DiaryTitleDTO> getLoveDirayByCollectionId(Long collectionId) {
        List<DiaryTitleDTO> diaryByCollectionDtos = null;
        try {
            diaryByCollectionDtos = loveDiaryMapper.getDiaryByCollectionId(collectionId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException("查询日记结果失败");
        }
        return diaryByCollectionDtos;
    }

    /**
     * 创建日记
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public LoveDiaryDTO createDiary(DiaryCreateRequest request) {
        //先查询日记册是否存在
        LambdaQueryWrapper<LoveDiaryCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LoveDiaryCollection::getId, request.getDiaryCollectionId());
        if (!diariesMapper.exists(queryWrapper)) {
            throw new ServiceException("非法请求！日记册不存在");
        }
        LoveDiary loveDiary = DiaryConvert.INSTANCE.convert(request);
        loveDiary.setImageUrls(JSON.toJSONString(request.getImages()));
        LoveDiaryDTO result = DiaryConvert.INSTANCE.convert(loveDiary);
        loveDiary.setCoupleId(UserInfoContextHolder.getCoupleId());
        loveDiaryMapper.insert(loveDiary);
        result.setId(loveDiary.getId());
        result.setImages(request.getImages());
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean fetchSynchronous(Long id, Boolean synchronous) {
        Long coupleId = UserInfoContextHolder.getCoupleId();
        if (Objects.isNull(coupleId)) {
            throw new ServiceException("没有情侣关系");
        }
        //判断是否两人已经拥有同步日记册
        LambdaQueryWrapper<LoveDiaryCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LoveDiaryCollection::getCoupleId,coupleId);
        queryWrapper.eq(LoveDiaryCollection::getStatus, 1);
        if (!diariesMapper.exists(queryWrapper)) {
            //不存在，则创建两条
            LoveDiaryCollection collection = LoveDiaryCollection.builder()
                    .userId(null)
                    .title("我们的日记")
                    .cover(QUICK_WRITE_DIARY_COVER)
                    .coupleId(coupleId)
                    .build();
            collection.setStatus(1);
            try {
                //创建两方的同步日记册
                diariesMapper.insert(collection);
            } catch (Exception e) {
                throw new ServiceException("新建同步日记册失败");
            }
        }
        LoveDiary loveDiary = LoveDiary.builder()
                .id(id)
                .coupleId(coupleId)
                .synchronous(synchronous)
                .build();
        try {
            if (loveDiaryMapper.updateById(loveDiary) < 1) {
                throw new ServiceException("日记不存在");
            }
        } catch (Exception e) {
            throw new ServiceException("更新同步状态异常");
        }
        return synchronous;
    }

    @Override
    public List<DiaryTitleDTO> getLoveDirayBySynchronous() {
        //先判断是否有情侣关系
        Long coupleId = UserInfoContextHolder.getCoupleId();
        if (Objects.isNull(coupleId)) {
            throw new ServiceException("请求非法，没有情侣关系");
        }
        return loveDiaryMapper.getDiaryBySynchronous(coupleId);
    }

    @Override
    public LoveDiaryVO getLoveDiaryById(Long id) {
        LoveDiary loveDiary = loveDiaryMapper.selectById(id);
        if (Objects.isNull(loveDiary)) {
            throw new ServiceException("请求非法，日记不存在");
        }
        //获取对应图片
        LoveDiaryVO loveDiaryVO = DiaryConvert.INSTANCE.convert2Vo(loveDiary);
        loveDiaryVO.setImages(JSON.parseObject(loveDiary.getImageUrls(),List.class));
        return loveDiaryVO;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String quickWrite(QuickWriterDiaryRequest request) {
        Long userId = UserInfoContextHolder.getUserId();
        Long coupleId = UserInfoContextHolder.getCoupleId();
        //快速创建日记
        //先判断速记日记本是否存在
        Long quickWriteCollectionId = diariesMapper.findQuickWriteCollection(userId, QUICK_WRITE_DIARY_TITLE);
        if (Objects.isNull(quickWriteCollectionId)) {
            //不存在则快速创建
            LoveDiaryCollection collection = LoveDiaryCollection.builder()
                    .userId(userId)
                    .coupleId(null)
                    .title(QUICK_WRITE_DIARY_TITLE)
                    .cover(QUICK_WRITE_DIARY_COVER)
                    .build();
            try {
                diariesMapper.insert(collection);
            } catch (Exception e) {
                log.info("创建日记册失败：{}", e.getMessage());
                throw new ServiceException("小记一下失败，创建日记册失败");
            }
            quickWriteCollectionId = collection.getId();
        }
        //正常记录
        LoveDiary loveDiary = LoveDiary.builder()
                .diaryCollectionId(quickWriteCollectionId)
                .title(DateUtil.format(Date.from(Instant.now()), JacksonTypeHandler.DEFAULT_DATE_FORMAT))
                .coupleId(coupleId)
                .synchronous(false)
                .content(request.getContent())
                .imageUrls(JSON.toJSONString(new ArrayList<>()))
                .build();
        if (loveDiaryMapper.insert(loveDiary) < 1) {
            throw new ServiceException("小记一下失败");
        }
        return request.getContent();
    }

    @Override
    public DiaryCollectionDTO getLoveDiariesBySyn() {
        Long coupleId = UserInfoContextHolder.getCoupleId();
        if (Objects.isNull(coupleId)) {
            return null;
        }
        LambdaQueryWrapper<LoveDiaryCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LoveDiaryCollection::getCoupleId, coupleId);
        queryWrapper.eq(LoveDiaryCollection::getStatus, 1);
        List<LoveDiaryCollection> loveDiaryCollections = diariesMapper.selectList(queryWrapper);
        return loveDiaryCollections.isEmpty() ? null : DiaryCollectionConvert.INSTANCE.convert(loveDiaryCollections.get(0));
    }

    /**
     * 更新日记册
     *
     * @param updateDiaryRequest
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String updateDiary(UpdateDiaryRequest updateDiaryRequest) {
        //更新日记册
        LoveDiary updateLoveDiaryDo = DiaryConvert.INSTANCE.convert(updateDiaryRequest);
        updateLoveDiaryDo.setImageUrls(JSON.toJSONString(updateDiaryRequest.getImages()));
        try {
            loveDiaryMapper.updateById(updateLoveDiaryDo);
        } catch (Exception e) {
            throw new ServiceException("更新日记册失败");
        }
        return Result.ok().getMsg();
    }

}
