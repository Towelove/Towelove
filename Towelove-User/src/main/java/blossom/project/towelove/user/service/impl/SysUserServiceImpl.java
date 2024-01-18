package blossom.project.towelove.user.service.impl;

import blossom.project.towelove.common.constant.TokenConstant;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.RestockUserInfoRequest;
import blossom.project.towelove.common.request.user.InsertUserRequest;
import blossom.project.towelove.common.request.user.UpdateUserRequest;
import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import blossom.project.towelove.common.response.user.SysUserVo;
import blossom.project.towelove.user.convert.SysUserConvert;
import blossom.project.towelove.user.domain.SysUser;
import blossom.project.towelove.user.domain.SysUserPermission;
import blossom.project.towelove.user.mapper.SysPermissionMapper;
import blossom.project.towelove.user.mapper.SysUserMapper;
import blossom.project.towelove.user.mapper.SysUserPermissionMapper;
import blossom.project.towelove.user.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;

    private final SysPermissionMapper sysPermissionMapper;

    private final SysUserPermissionMapper sysUserPermissionMapper;

    @Transactional
    @Override
    public String updateUser(UpdateUserRequest request) {
        SysUser sysUser = SysUserConvert.INSTANCE.convert(request);
        //获取userId,
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String userId = requestAttributes.getRequest().getHeader(TokenConstant.USER_ID_HEADER);
        sysUser.setId(Long.parseLong(userId));
        if (!updateById(sysUser)) {
            throw new ServiceException("更新用户信息失败");
        }
        return "更新成功";
    }

    @Override
    public SysUserVo selectByUserId(Long userId) {
        SysUser sysUser = getById(userId);
        if (Objects.isNull(sysUser)) {
            throw new ServiceException("用户数据不存在");
        }
        return SysUserConvert.INSTANCE.convert(sysUser);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String deleteById(Long userId, HttpServletRequest httpServletRequest) {
        if (userId.equals(Long.parseLong(httpServletRequest.getHeader(TokenConstant.USER_ID_HEADER)))) {
            //TODO: 注销用户连带删除其他表中用户相关信息
            removeById(userId);
            sysUserPermissionMapper.delete(new LambdaQueryWrapper<SysUserPermission>().eq(SysUserPermission::getUserId, userId));
        }
        return "注销用户成功";
    }

    @Override
    public PageResponse<SysUserVo> selectByPage(Integer pageNo, Integer pageSize) {
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysUser::getDeleted, 0);
        lqw.last(String.format("limit %s,%s", pageNo, pageSize));
        List<SysUserVo> sysUserVos = sysUserMapper.selectList(lqw).stream()
                .map(SysUserConvert.INSTANCE::convert)
                .collect(Collectors.toList());
        return new PageResponse<>(pageNo, pageSize, sysUserVos);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public SysUser inserUser(InsertUserRequest userRequest) {
        String email = userRequest.getEmail();
        String phone = userRequest.getPhoneNumber();
        SysUser sysUserFromDB = sysUserMapper.selectByPhoneNumberOrEmail(phone, email);
        if (Objects.nonNull(sysUserFromDB)) {
            throw new ServiceException("用户已经存在");
        }
        SysUser sysUser = SysUserConvert.INSTANCE.convert(userRequest);
        try {
            save(sysUser);
            addUserPermission(sysUser);
            //增加用户权限，暂时只有user权限

        } catch (Exception e) {
            throw new ServiceException("插入用户失败");
        }
        return sysUser;
    }
    @Override
    public void addUserPermission(SysUser sysUser) {
        SysUserPermission sysUserPermission = new SysUserPermission();
        sysUserPermission.setUserId(sysUser.getId());
        sysUserPermission.setPermissionId(TokenConstant.USER_PERMISSION_CODE);
        if (sysUserPermissionMapper.insert(sysUserPermission) < 1) {
            throw new ServiceException("用户添加权限失败，回滚");
        }
    }

    @Override
    public SysUser findUser(@Valid AuthLoginRequest authLoginRequest) {
        String phone = authLoginRequest.getPhoneNumber();
        String email = authLoginRequest.getEmail();
        SysUser sysUser = sysUserMapper.selectByPhoneNumberOrEmail(phone, email);
        if (Objects.isNull(sysUser)) {
            //调用最小注册逻辑
            sysUser =  SysUserConvert.INSTANCE.convert(authLoginRequest);
            try {
                save(sysUser);
            } catch (Exception e) {
                throw new ServiceException("用户不存在");
            }
            //这里不给用户权限，除非用户补充完毕信息，才能给对应的user权限
        }
        return sysUser;
    }

    @Override
    public List<SysUserPermissionDto> getPermissionByUserId(Long userId) {
        return sysPermissionMapper.selectUserPermissionByUserId(userId);
    }

    @Override
    @Transactional
    public String restockUserInfo(RestockUserInfoRequest restockUserInfoRequest) {
        //更新用户信息
        SysUser sysUser = SysUserConvert.INSTANCE.convert(restockUserInfoRequest);
        try {
            updateById(sysUser);
            addUserPermission(sysUser);
        } catch (ServiceException e) {
            throw new ServiceException("补充用户信息失败");
        }
        return "补充用户信息成功";
    }
}

