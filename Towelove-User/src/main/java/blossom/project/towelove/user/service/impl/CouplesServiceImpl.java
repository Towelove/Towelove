package blossom.project.towelove.user.service.impl;

import blossom.project.towelove.common.request.user.CouplesCreateRequest;
import blossom.project.towelove.common.request.user.CouplesUpdateRequest;
import blossom.project.towelove.common.response.user.CouplesRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import blossom.project.towelove.user.entity.Couples;
import blossom.project.towelove.user.mapper.CouplesMapper;
import blossom.project.towelove.user.service.CouplesService;

import java.util.List;


/**
 * @author: ZhangBlossom
 * @date: 2024-01-17 13:36:03
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CouplesServiceImpl extends ServiceImpl<CouplesMapper, Couples> implements CouplesService {
 
    private final CouplesMapper couplesMapper;

    @Override
    public CouplesRespDTO getCouplesById(Long CouplesId) {
        return null;
    }


    @Override
    public CouplesRespDTO updateCouples(CouplesUpdateRequest updateRequest) {
        return null;
    }

    @Override
    public Boolean deleteCouplesById(Long CouplesId) {
        return null;
    }

    @Override
    public Boolean batchDeleteCouples(List<Long> ids) {
        return null;
    }

    @Override
    public CouplesRespDTO createCouples(CouplesCreateRequest createRequest) {
        return null;
    }
}


