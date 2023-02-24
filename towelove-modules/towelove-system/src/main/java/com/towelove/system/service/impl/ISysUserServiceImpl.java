package com.towelove.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.system.api.domain.SysUser;
import com.towelove.system.mapper.SysUserMapper;
import com.towelove.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/2/24 13:39
 * ISysUserServiceImpl类
 */
@Service
public class ISysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements ISysUserService {
    @Override
    public List<SysUser> selectUserList(SysUser user) {
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotEmpty(user.getUserName()),SysUser::getUserName
        ,user.getUserName());
        List<SysUser> users = baseMapper.selectList(lqw);
        return users;
    }
}
