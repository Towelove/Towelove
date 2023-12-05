package blossom.project.towelove.user.service.impl;

/**
 * @Author SIK
 * @Date 2023 12 05 11 28
 **/
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.user.domain.UserThirdParty;
import blossom.project.towelove.user.mapper.UserThirdPartyMapper;
import blossom.project.towelove.user.service.UserThirdPartyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserThirdPartyServiceImpl extends ServiceImpl<UserThirdPartyMapper, UserThirdParty> implements UserThirdPartyService {

    private final UserThirdPartyMapper userThirdPartyMapper;
    @Override
    public List<UserThirdParty> getByUserId(Long userId) {
        return userThirdPartyMapper.selectByUserId(userId);
    }

    @Override
    public UserThirdParty getByThirdPartyId(String socialUid) {
        UserThirdParty userThirdParty = userThirdPartyMapper.selectByThridPartyId(socialUid);
        if (Objects.isNull(userThirdParty)) {
            throw new ServiceException("未绑定对应的第三方服务");
        }
        return userThirdParty;
    }


}
