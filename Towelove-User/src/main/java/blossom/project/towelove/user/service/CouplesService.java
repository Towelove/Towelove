package blossom.project.towelove.user.service;



import blossom.project.towelove.common.request.CouplesInvitedRequest;
import blossom.project.towelove.common.request.user.CouplesCreateRequest;
import blossom.project.towelove.common.request.user.CouplesUpdateRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.CouplesRespDTO;
import blossom.project.towelove.common.response.user.CouplesTogetherResponse;
import blossom.project.towelove.user.entity.Couples;
import com.baomidou.mybatisplus.extension.service.IService;

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

public interface CouplesService extends IService<Couples> {

    CouplesRespDTO getCouplesById(Long CouplesId);

    CouplesRespDTO updateCouples(CouplesUpdateRequest updateRequest);

    Boolean deleteCouplesById(Long CouplesId);

    Boolean batchDeleteCouples(List<Long> ids);

    CouplesRespDTO createCouples(CouplesCreateRequest createRequest);

    Result<String> binding(CouplesInvitedRequest couplesInvitedRequest);

    Result<String> unbinding(Long coupleId);

    Long getTogetherCouples();

}



