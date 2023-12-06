package blossom.project.towelove.user.service.impl;

/**
 * @Author SIK
 * @Date 2023 12 05 11 28
 **/
import blossom.project.towelove.common.domain.dto.ThirdPartyLoginUser;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.response.user.SysUserVo;
import blossom.project.towelove.user.convert.SysUserConvert;
import blossom.project.towelove.user.domain.SysUser;
import blossom.project.towelove.user.domain.UserThirdParty;
import blossom.project.towelove.user.mapper.SysUserMapper;
import blossom.project.towelove.user.mapper.UserThirdPartyMapper;
import blossom.project.towelove.user.service.SysUserService;
import blossom.project.towelove.user.service.UserThirdPartyService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserThirdPartyServiceImpl extends ServiceImpl<UserThirdPartyMapper, UserThirdParty> implements UserThirdPartyService {

    private final UserThirdPartyMapper userThirdPartyMapper;

    private final SysUserMapper sysUserMapper;

    private final SysUserService sysUserService;
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

    /**
     * 根据第三方用户信息来登入系统
     * @param thirdPartyLoginUser
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long accessByThirdPartyAccount(ThirdPartyLoginUser thirdPartyLoginUser) {
        SysUser sysUser = sysUserMapper.selectByThirdPartyId(thirdPartyLoginUser.getSocialUid());
        if (Objects.isNull(sysUser)){
            //返回已经注册的用户信息
            sysUser = SysUserConvert.INSTANCE.convert(thirdPartyLoginUser);
            if (sysUserMapper.insert(sysUser) <= 0) {
                throw new ServiceException("注册第三方登入用户失败，请联系管理员");
            }
            //添加用户权限
            sysUserService.addUserPermission(sysUser);
            //关联的第三方登入表
            UserThirdParty userThirdParty = new UserThirdParty();
            userThirdParty.setUserId(sysUser.getId());
            userThirdParty.setThirdPartyId(thirdPartyLoginUser.getSocialUid());
            userThirdParty.setProvider(thirdPartyLoginUser.getType());
            if (!save(userThirdParty)) {
                throw new ServiceException("记录第三方用户信息失败，请联系管理员");
            }
        }
        //第三方用户未注册，则新增用户
        return sysUser.getId();
    }
}
