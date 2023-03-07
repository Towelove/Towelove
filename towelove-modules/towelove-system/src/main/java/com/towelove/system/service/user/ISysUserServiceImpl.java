package com.towelove.system.service.user;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.towelove.common.core.exception.ServiceException;
import com.towelove.common.core.utils.SpringUtils;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.common.security.utils.SecurityUtils;
import com.towelove.system.api.domain.SysUser;
import com.towelove.system.mapper.user.SysUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: 张锦标
 * @date: 2023/2/24 13:39
 * ISysUserServiceImpl类
 */
@Service
public class ISysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements ISysUserService {
    /**
     * 根据输入的信息查询符合条件的用户
     *
     * @param user 用户信息
     * @return 符合条件的用户
     */
    @Override
    public List<SysUser> selectUserList(SysUser user) {
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotEmpty(user.getUserName()), SysUser::getUserName
                , user.getUserName());
        List<SysUser> users = baseMapper.selectList(lqw);
        return users;
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 要查询的用户名
     * @return 没有查询到则报错，否则返回对应用户
     */
    @Override
    public SysUser selectUserByUserName(String username) {
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotEmpty(username), SysUser::getUserName, username);
        SysUser sysUser = baseMapper.selectOne(lqw);
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("没用查找到当前用户，用户名错误");
        }
        return sysUser;
    }

    /**
     * 检查是否有重复的用户名
     *
     * @param sysUser 待检查的用户信息
     * @return 返回1表示重复 0表示不重复 用户名可用
     */
    @Override
    public String checkUserNameUnique(SysUser sysUser) {
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotEmpty(sysUser.getUserName()),
                SysUser::getUserName, sysUser.getUserName());
        List<SysUser> sysUsers = baseMapper.selectList(lqw);
        //返回1表示数据重复 不允许使用当前用户名 否则允许使用
        return StringUtils.isNotEmpty(sysUsers) ? "1" : "0";
    }

    /**
     * 管理员插入用户信息
     * @param user 待插入的用户信息
     * @return 大于0表示插入成功 否则插入失败
     */
    @Override
    public int insertUser(SysUser user) {
        return baseMapper.insert(user);
    }
    /**
     * 检查是否有重复的邮箱号
     *
     * @param user 待检查的用户信息
     * @return 返回1表示重复 0表示不重复 用户名可用
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotEmpty(user.getEmail()),
                SysUser::getEmail, user.getEmail());
        List<SysUser> sysUsers = baseMapper.selectList(lqw);
        //返回1表示数据重复 不允许使用当前用户名 否则允许使用
        return StringUtils.isNotEmpty(sysUsers) ? "1" : "0";
    }
    /**
     * 检查是否有重复的电话号
     *
     * @param user 待检查的用户信息
     * @return 返回1表示重复 0表示不重复 用户名可用
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotEmpty(user.getPhonenumber()),
                SysUser::getPhonenumber, user.getPhonenumber());
        List<SysUser> sysUsers = baseMapper.selectList(lqw);
        //返回1表示数据重复 不允许使用当前用户名 否则允许使用
        return StringUtils.isNotEmpty(sysUsers) ? "1" : "0";
    }
    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users))
            {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    @Override
    public int deleteUserByIds(Long[] userIds) {
        List<Long> ids = Arrays.stream(userIds).collect(Collectors.toList());
        int i = baseMapper.deleteBatchIds(ids);
        return i==userIds.length?1:0;
    }

    @Override
    public int resetPwd(SysUser user) {
       return  baseMapper.updateById(user);
    }

    @Override
    public int updateUserStatus(SysUser user) {
        user.setStatus("1");
        return baseMapper.updateById(user);
    }

    @Override
    public int authRole(Long userId) {
        SysUser sysUser = baseMapper.selectById(userId);
        sysUser.setRoleId(1L);
        return baseMapper.updateById(
                sysUser
        );
    }

    /**
     * 根据表单传递来的用户信息注册用户
     *
     * @param sysUser 用户信息
     * @return 注册成功返回true 否则返回false
     */
    @Override
    public Boolean registerUser(SysUser sysUser) {
        return baseMapper.insert(sysUser) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 根据用户id查询用户
     *
     * @param userId 用户id
     * @return 返回查询到的用户
     */
    @Override
    public SysUser selectUserById(Long userId) {
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(!Objects.isNull(userId), SysUser::getUserId, userId);
        SysUser sysUser = baseMapper.selectOne(lqw);
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("没用查找到当前用户，用户ID不存在");
        }
        return sysUser;
    }
    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(SysUser user)
    {
        return baseMapper.updateById(user);
    }
}
