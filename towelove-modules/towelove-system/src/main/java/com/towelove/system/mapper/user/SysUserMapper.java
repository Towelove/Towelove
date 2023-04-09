package com.towelove.system.mapper.user;

import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.system.api.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: 张锦标
 * @date: 2023/2/24 13:43
 * SysUserMapper接口
 */
@Mapper
public interface SysUserMapper extends BaseMapperX<SysUser> {
    @Override
    int updateById(SysUser entity);

    int uploadAvatar(@Param("userId") Long userId,@Param("avatar") String avatar);
}
