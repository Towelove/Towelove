package blossom.project.towelove.user.service.impl;

import blossom.project.towelove.common.constant.TokenConstant;
import blossom.project.towelove.common.constant.UserConstants;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.RestockUserInfoDTO;
import blossom.project.towelove.common.request.auth.RestockUserInfoRequest;
import blossom.project.towelove.common.request.user.InsertUserRequest;
import blossom.project.towelove.common.request.user.UpdateUserRequest;
import blossom.project.towelove.common.response.user.*;
import blossom.project.towelove.framework.redis.service.RedisService;
import blossom.project.towelove.framework.user.core.UserInfoContextHolder;
import blossom.project.towelove.user.convert.SysUserConvert;
import blossom.project.towelove.user.entity.Couples;
import blossom.project.towelove.user.entity.SysUser;
import blossom.project.towelove.user.domain.SysUserPermission;
import blossom.project.towelove.user.mapper.CouplesMapper;
import blossom.project.towelove.user.mapper.SysPermissionMapper;
import blossom.project.towelove.user.mapper.SysUserMapper;
import blossom.project.towelove.user.mapper.SysUserPermissionMapper;
import blossom.project.towelove.user.service.SysUserService;
import cn.hutool.core.util.StrUtil;
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
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;

    private final SysPermissionMapper sysPermissionMapper;

    private final SysUserPermissionMapper sysUserPermissionMapper;

    private final CouplesMapper couplesMapper;

    private final RedisService redisService;

    @Transactional
    @Override
    public String updateUser(UpdateUserRequest request) {
        SysUser sysUser = SysUserConvert.INSTANCE.convert(request);
        //获取userId,
        Long userId = UserInfoContextHolder.getUserId();
        sysUser.setId(userId);
        if (!updateById(sysUser)) {
            throw new ServiceException("更新用户信息失败");
        }
        return "更新成功";
    }

    @Override
    public SysUserDTO selectByUserId() {
        Long userId = UserInfoContextHolder.getUserId();
        if (Objects.isNull(userId)){
            throw new ServiceException("try to get UserId from Header failed...");
        }
        SysUser sysUser = getById(userId);
        if (Objects.isNull(sysUser)) {
            throw new ServiceException("用户数据不存在");
        }
        SysUserDTO sysUserDTO = SysUserConvert.INSTANCE.convert2DTO(sysUser);
        //判断一下是否有情侣信息
        CouplesRespDTO couplesRespDTO = couplesMapper.selectCoupleIdByUserId(userId);
        if (Objects.nonNull(couplesRespDTO) && Objects.nonNull(couplesRespDTO.getId())){
            //查询对象的信息
            Long couplesUserId = userId.equals(couplesRespDTO.getGirlId()) ? couplesRespDTO.getBoyId()
                    : couplesRespDTO.getGirlId();
            CouplesInfoDto couplesInfoDto = sysUserMapper.selectCouplesInfo(couplesUserId);
            sysUserDTO.setCoupleId(couplesRespDTO.getId());
            sysUserDTO.setBoyId(couplesRespDTO.getBoyId());
            sysUserDTO.setGirlId(couplesRespDTO.getGirlId());
            sysUserDTO.setCoupleAvatar(couplesInfoDto.getCoupleAvatar());
            sysUserDTO.setCoupleName(couplesInfoDto.getCoupleName());
        }
        return sysUserDTO;
    }

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
//            addUserPermission(sysUser);
            //增加用户权限，暂时只有user权限
        } catch (Exception e) {
            throw new ServiceException("插入用户失败");
        }
        return sysUser;
    }
    @Override
    public void addUserPermission(SysUser sysUser) {
        //判断用户是否已经有权限信息
        LambdaQueryWrapper<SysUserPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserPermission::getUserId,sysUser.getId());
        if (sysUserPermissionMapper.exists(wrapper)) {
            throw new ServiceException("请勿重复添加权限");
        }
        SysUserPermission sysUserPermission = new SysUserPermission();
        sysUserPermission.setUserId(sysUser.getId());
        sysUserPermission.setPermissionId(TokenConstant.USER_PERMISSION_CODE);
        if (sysUserPermissionMapper.insert(sysUserPermission) < 1) {
            throw new ServiceException("用户添加权限失败，回滚");
        }
    }

    @Override
    public LoginUserResponse findUser(@Valid AuthLoginRequest authLoginRequest) {
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
        LoginUserResponse loginUserResponse = sysUserMapper.selectUserWithPermission(sysUser.getId());

//        SysUserDTO sysUserDTO = SysUserConvert.INSTANCE.convert2DTO(sysUser);
//        if (StrUtil.isNotBlank(sysUser.getEmail()) && StrUtil.isNotBlank(sysUser.getPhoneNumber())){
//            CouplesRespDTO couplesRespDTO = couplesMapper.selectCoupleIdByUserId(sysUser.getId(), sysUser.getSex());
//            sysUserDTO.setCoupleId(couplesRespDTO.getId());
//            sysUserDTO.setBoyId(couplesRespDTO.getBoyId());
//            sysUserDTO.setg
//        }
        return loginUserResponse;
    }

    @Override
    public SysUserPermissionDto getPermissionByUserId(Long userId) {
        //查询缓存中是否有用户权限信息
         Object cacheObject = redisService.getCacheObject(String.format(UserConstants.USER_PERMISSION_KEY, userId));
        if (Objects.nonNull(cacheObject)){
            //直接返回
            return (SysUserPermissionDto) cacheObject;
        }
        //从数据库中查询
        SysUserPermissionDto sysUserPermissionDto = sysPermissionMapper.selectUserPermissionByUserId(userId);
        if (Objects.isNull(sysUserPermissionDto)){
            return null;
        }
        //存入缓存中
        redisService.setCacheObject(String.format(UserConstants.USER_PERMISSION_KEY,userId),sysUserPermissionDto,2L, TimeUnit.HOURS);
        return sysUserPermissionDto;
    }

    @Override
    @Transactional
    public LoginUserResponse restockUserInfo(RestockUserInfoDTO restockUserInfoDTO) {
        //更新用户信息
        SysUser sysUser = SysUserConvert.INSTANCE.convert(restockUserInfoDTO);
        try {
//            Optional.ofNullable(restockUserInfoRequest.getPhone())
            updateById(sysUser);
            addUserPermission(sysUser);
        } catch (ServiceException e) {
            throw new ServiceException("补充用户信息失败");
        }
        LoginUserResponse loginUserResponse = sysUserMapper.selectUserWithPermission(sysUser.getId());

        return loginUserResponse;
    }


    @Override
    public Boolean findByPhoneOrEmail(String phone, String email) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(phone),SysUser::getPhoneNumber,phone);
        queryWrapper.eq(StrUtil.isNotBlank(email),SysUser::getEmail,email);
        return sysUserMapper.exists(queryWrapper);
    }
}


