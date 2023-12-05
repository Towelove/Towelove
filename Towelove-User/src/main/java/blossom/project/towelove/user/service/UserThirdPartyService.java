package blossom.project.towelove.user.service;

/**
 * @Author SIK
 * @Date 2023 12 05 11 27
 **/

import blossom.project.towelove.user.domain.UserThirdParty;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserThirdPartyService extends IService<UserThirdParty> {
    List<UserThirdParty> getByUserId(Long userId);

    UserThirdParty getByThirdPartyId(String socialUid);
}

