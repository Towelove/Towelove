package blossom.project.towelove.server.service.impl;

import blossom.project.towelove.common.exception.ServerException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.server.conver.BuryingPointConvert;
import blossom.project.towelove.server.dto.BuryingPointRequest;
import blossom.project.towelove.server.entity.BuryingPoint;
import blossom.project.towelove.server.mapper.BuryingPointMapper;
import blossom.project.towelove.server.service.BuryingPointService;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import java.util.List;
import java.util.Objects;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.service.impl
 * @className: BuryingPointServiceImpl
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/4/8 14:41
 * @version: 1.0
 */
@Service
@RequiredArgsConstructor
public class BuryingPointServiceImpl implements BuryingPointService {

    private final BuryingPointMapper buryingPointMapper;
    private BuryingPointConvert INSTANCE = BuryingPointConvert.INSTANCE;

    @Override
    public void saveBpData(BuryingPointRequest buryingPointRequest) {

        BuryingPoint buryingPoint = INSTANCE.convert(buryingPointRequest);
        switch (buryingPoint.getEventId()){
            /**
             * 用户点击事件
             */
            case 1  ->{
                if (StrUtil.isBlank(buryingPoint.getElementId())){
                    throw new ServerException("elementId could not be null", BaseErrorCode.BURYING_POINT_ERROR_PARAM_ERROR);
                }
            }
            /**
             * 用户页面停留时间
             */
            case 2 ->{
                if (StrUtil.isBlank(buryingPoint.getPageId()) || Objects.isNull(buryingPoint.getStayTime())){
                    throw new ServerException("pageId or stayTime could not be null", BaseErrorCode.BURYING_POINT_ERROR_PARAM_ERROR);
                }
            }
            /**
             * 用户来源
             */
            case 3->{
                if (Objects.isNull(buryingPoint.getSource())){
                    throw new ServerException("source could not be null", BaseErrorCode.BURYING_POINT_ERROR_PARAM_ERROR);
                }
            }
            default -> throw new ServerException("eventId is illegal", BaseErrorCode.BURYING_POINT_ERROR_PARAM_ERROR);
        }
        if (buryingPointMapper.insert(buryingPoint) < 1) {
            throw new ServerException("save bp data error", BaseErrorCode.BURYING_POINT_ERROR);
        }
    }

    @Override
    public List<BuryingPoint> getBpDataTotal() {
        return buryingPointMapper.selectList(null);
    }
}
