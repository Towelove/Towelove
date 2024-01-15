package blossom.project.towelove.loves.service.Impl;


import blossom.project.towelove.framework.oss.service.OssService;
import blossom.project.towelove.loves.entity.TimeLine;
import blossom.project.towelove.loves.mapper.TimeLineMapper;
import blossom.project.towelove.loves.service.TimeLineService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 *   TODO:TimeLine 事件服务实现
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TimeLineServiceImpl extends ServiceImpl<TimeLineMapper, TimeLine> implements TimeLineService {

    private final OssService ossService;
    @Override
    public TimeLine createTimeLine(List<MultipartFile> images, TimeLine timeLine) {
        return null;
    }

    @Override
    public TimeLine getTimeLineById(Long timeLineId) {
        return null;
    }

    @Override
    public List<TimeLine> getAllTimeLines() {
        return null;
    }

    @Override
    public TimeLine updateTimeLine(Long timeLineId, TimeLine timeLine) {
        return null;
    }

    @Override
    public boolean deleteTimeLine(Long timeLineId) {
        return false;
    }

    @Override
    public boolean saveBatch(Collection<TimeLine> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<TimeLine> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<TimeLine> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(TimeLine entity) {
        return false;
    }

    @Override
    public TimeLine getOne(Wrapper<TimeLine> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<TimeLine> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<TimeLine> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }


    @Override
    public Class<TimeLine> getEntityClass() {
        return null;
    }
}

