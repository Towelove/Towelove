package blossom.project.towelove.user.mapper;

import blossom.project.towelove.common.request.user.UpdateUserRequest;
import blossom.project.towelove.common.response.user.SysUserVo;
import blossom.project.towelove.user.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser selectByPhoneNumberOrEmail(@Param("phone") String phone,@Param("email") String email);
}
