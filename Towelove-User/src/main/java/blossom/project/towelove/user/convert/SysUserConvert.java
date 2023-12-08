package blossom.project.towelove.user.convert;

import blossom.project.towelove.common.domain.dto.ThirdPartyLoginUser;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.RestockUserInfoRequest;
import blossom.project.towelove.common.request.user.InsertUserRequest;
import blossom.project.towelove.common.request.user.UpdateUserRequest;
import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import blossom.project.towelove.common.response.user.SysUserVo;
import blossom.project.towelove.user.domain.SysUser;
import blossom.project.towelove.user.mapper.SysUserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface SysUserConvert {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUserVo convert(SysUser sysUser);

    SysUser convert(UpdateUserRequest updateUserRequest);

    SysUser convert(InsertUserRequest insertUserRequest);

    SysUser convert(ThirdPartyLoginUser thirdPartyLoginUser);

    SysUser convert(AuthLoginRequest authLoginRequest);

    SysUser convert(RestockUserInfoRequest restockUserInfoRequest);


}
