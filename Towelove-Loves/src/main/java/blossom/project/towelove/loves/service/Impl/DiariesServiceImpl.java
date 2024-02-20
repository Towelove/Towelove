package blossom.project.towelove.loves.service.Impl;

import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.loves.diary.DiaryCollectionCreateRequest;
import blossom.project.towelove.common.request.loves.diary.DiaryCollectionPageRequest;
import blossom.project.towelove.common.response.love.diary.DiaryCollectionDTO;
import blossom.project.towelove.common.response.love.diary.LoveDiaryDTO;
import blossom.project.towelove.loves.convert.DiaryCollectionConvert;
import blossom.project.towelove.loves.entity.LoveDiary;
import blossom.project.towelove.loves.entity.LoveDiaryCollection;
import blossom.project.towelove.loves.mapper.DiariesMapper;
import blossom.project.towelove.loves.mapper.LoveDiaryMapper;
import blossom.project.towelove.loves.service.DiariesService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
                ,page.getSize() //数量
                ,result); //结果集
    }

    @Override
    public DiaryCollectionDTO createDiaryCollection(DiaryCollectionCreateRequest request) {
        LoveDiaryCollection loveDiaryCollection = DiaryCollectionConvert.INSTANCE.convert(request);
        if (Objects.isNull(loveDiaryCollection)){
            throw new ServiceException("入参有误");
        }
        if (diariesMapper.insert(loveDiaryCollection) != 0) {
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
    public List<String> getLoveDirayByCollectionId(Long collectionId) {
        LambdaQueryWrapper<LoveDiary> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(collectionId),LoveDiary::getDiary_id,collectionId);
        List<String> diaryByCollectionId = null;
        try {
            diaryByCollectionId = loveDiaryMapper.getDiaryByCollectionId(collectionId);
        } catch (Exception e) {
            throw new ServiceException("查询日记结果失败");
        }
        return diaryByCollectionId;
    }
}
