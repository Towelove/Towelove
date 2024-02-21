package blossom.project.towelove.loves.service.Impl;

import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.loves.diary.DiaryCollectionCreateRequest;
import blossom.project.towelove.common.request.loves.diary.DiaryCollectionPageRequest;
import blossom.project.towelove.common.request.loves.diary.DiaryCreateRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.love.diary.DiaryCollectionDTO;
import blossom.project.towelove.common.response.love.diary.DiaryTitleDTO;
import blossom.project.towelove.common.response.love.diary.LoveDiaryDTO;
import blossom.project.towelove.loves.convert.DiaryCollectionConvert;
import blossom.project.towelove.loves.convert.DiaryConvert;
import blossom.project.towelove.loves.entity.LoveDiary;
import blossom.project.towelove.loves.entity.LoveDiaryCollection;
import blossom.project.towelove.loves.entity.LoveDiaryImage;
import blossom.project.towelove.loves.mapper.DiariesMapper;
import blossom.project.towelove.loves.mapper.DiaryMapper;
import blossom.project.towelove.loves.mapper.LoveDiaryImageMapper;
import blossom.project.towelove.loves.mapper.LoveDiaryMapper;
import blossom.project.towelove.loves.service.DiariesService;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class DiariesServiceImpl extends ServiceImpl<DiariesMapper,LoveDiaryCollection> implements  DiariesService {

    private final DiariesMapper diariesMapper;

    private final LoveDiaryMapper loveDiaryMapper;


    private final LoveDiaryImageMapper diaryImageMapper;

//    private final DiaryService diaryService;

    @Override
    public List<DiaryCollectionDTO> getDiaryCollectionById(Long coupleId) {
        LambdaQueryWrapper<LoveDiaryCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(coupleId),LoveDiaryCollection::getCoupleId,coupleId);
        List<LoveDiaryCollection> loveDiaryCollections = diariesMapper.selectList(queryWrapper);
        return DiaryCollectionConvert.INSTANCE.convert(loveDiaryCollections);
    }

    @Override
    public PageResponse<DiaryCollectionDTO> getDiaryCollectionByPage(DiaryCollectionPageRequest request) {
        LambdaQueryWrapper<LoveDiaryCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(request.getCoupleId()), LoveDiaryCollection::getCoupleId, request.getCoupleId());
        Page<LoveDiaryCollection> page = new Page<>(request.getPageNo() - 1,request.getPageSize());
        page = diariesMapper.selectPage(page, queryWrapper);
        List<DiaryCollectionDTO> result = DiaryCollectionConvert.INSTANCE.convert(page.getRecords());
        return new PageResponse<>(page.getCurrent() //页码
                ,page.getSize()
                ,page.getTotal()//数量
                ,result); //结果集
    }

    @Override
    public DiaryCollectionDTO createDiaryCollection(DiaryCollectionCreateRequest request) {
        if (StrUtil.isBlank(request.getTitle())){
            request.setTitle(DateUtil.format(Date.from(Instant.now()),"yyyy-MM-dd HH:mm:ss"));
        }
        LoveDiaryCollection loveDiaryCollection = DiaryCollectionConvert.INSTANCE.convert(request);
        if (Objects.isNull(loveDiaryCollection)){
            throw new ServiceException("入参有误");
        }
        if (diariesMapper.insert(loveDiaryCollection) == 0) {
            throw new ServiceException("创建日记失败");
        }
        return DiaryCollectionConvert.INSTANCE.convert2DTO(request);
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
        List<DiaryTitleDTO> diaryByCollectionId = null;
        try {
            diaryByCollectionId = loveDiaryMapper.getDiaryByCollectionId(collectionId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException("查询日记结果失败");
        }
        return diaryByCollectionId;
    }

    /**
     * 创建日记
     * @param request
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public LoveDiaryDTO createDiary(DiaryCreateRequest request) {
        //先查询日记册是否存在
        LambdaQueryWrapper<LoveDiaryCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LoveDiaryCollection::getId,request.getDiaryCollectionId());
        if (!diariesMapper.exists(queryWrapper)) {
            throw new ServiceException("非法请求！日记册不存在");
        }
        LoveDiary entity = DiaryConvert.INSTANCE.convert(request);
        try {
            loveDiaryMapper.insert(entity);
            //获取图片集合
            List<LoveDiaryImage> loveDiaryImages = request.getImages().stream().map((url) -> {
                return LoveDiaryImage.builder()
                        .diaryId(entity.getId())
                        .url(url).build();
            }).toList();
            diaryImageMapper.insertBatch(loveDiaryImages);
        } catch (Exception e) {
            log.error(String.format("日记册：%s创建日记失败,失败原因为：%s",request.getDiaryCollectionId(),e.getMessage()));
            throw new ServiceException("创建日记失败");
        }

        LoveDiaryDTO result = DiaryConvert.INSTANCE.convert(entity);
        result.setImages(request.getImages());
        return result;
    }

    @Override
    public Boolean fetchSynchronous(Long id,Boolean synchronous) {
        LoveDiary entity = LoveDiary.builder()
                .id(id)
                .synchronous(synchronous)
                .build();
        return loveDiaryMapper.updateById(entity) > 0;
    }
}
