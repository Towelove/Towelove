package blossom.project.towelove.user.service;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.RestockUserInfoRequest;
import blossom.project.towelove.common.request.user.InsertUserRequest;
import blossom.project.towelove.common.request.user.UpdateUserRequest;
import blossom.project.towelove.common.response.user.SysUserDTO;
import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import blossom.project.towelove.common.response.user.SysUserVo;
import blossom.project.towelove.user.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SysUserService extends IService<SysUser> {
    SysUserDTO selectByUserId();

    SysUserVo selectByUserId(Long userId);

    String updateUser(UpdateUserRequest request);

    String deleteById(Long userId, HttpServletRequest httpServletRequest);

    PageResponse<SysUserVo> selectByPage(Integer pageNo, Integer pageSize);

    SysUser inserUser(InsertUserRequest sysUser);

    SysUser findUser(AuthLoginRequest authLoginRequest);

    List<SysUserPermissionDto> getPermissionByUserId(Long userId);

    void addUserPermission(SysUser sysUser);

    String restockUserInfo(RestockUserInfoRequest restockUserInfoRequest);
}

