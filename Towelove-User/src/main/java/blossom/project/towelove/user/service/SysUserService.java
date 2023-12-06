package blossom.project.towelove.user.service;

import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.user.InsertUserRequest;
import blossom.project.towelove.common.request.user.UpdateUserRequest;
import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import blossom.project.towelove.common.response.user.SysUserVo;
import blossom.project.towelove.user.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SysUserService extends IService<SysUser> {
    SysUserVo selectByUserId(Long userId);

    String updateUser(UpdateUserRequest request, HttpServletRequest httpServletRequest);

    String deleteById(Long userId, HttpServletRequest httpServletRequest);

    PageResponse<SysUserVo> selectByPage(Integer pageNo, Integer pageSize);

    String inserUser(InsertUserRequest sysUser);

    String findUser(AuthLoginRequest authLoginRequest);

    List<SysUserPermissionDto> getPermissionByUserId(Long userId);


}

