package blossom.project.towelove.user.service;

/**
 * @Author SIK
 * @Date 2023 12 05 11 27
 **/

import blossom.project.towelove.common.domain.dto.ThirdPartyLoginUser;
import blossom.project.towelove.common.response.user.SysUserVo;
import blossom.project.towelove.user.domain.UserThirdParty;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserThirdPartyService extends IService<UserThirdParty> {
    List<UserThirdParty> getByUserId(Long userId);

    UserThirdParty getByThirdPartyId(String socialUid);

    Long accessByThirdPartyAccount(ThirdPartyLoginUser thirdPartyLoginUser);

}

