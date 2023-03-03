package com.towelove.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.towelove.system.api.domain.SysUser;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/2/24 13:38
 * ISysUserService接口
 */
public interface ISysUserService extends IService<SysUser> {
    List<SysUser> selectUserList(SysUser user);

    SysUser selectUserByUserName(String username);

    Boolean registerUser(SysUser sysUser);

    SysUser selectUserById(Long userId);
     int updateUser(SysUser user);

    String checkUserNameUnique(SysUser sysUser);

    int insertUser(SysUser user);

    String checkEmailUnique(SysUser user);

    String checkPhoneUnique(SysUser user);

    void checkUserDataScope(Long userId);

    int deleteUserByIds(Long[] userIds);

    int resetPwd(SysUser user);

    int updateUserStatus(SysUser user);

    int authRole(Long userId);
}
