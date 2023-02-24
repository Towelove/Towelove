package com.towelove.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.towelove.system.api.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: 张锦标
 * @date: 2023/2/24 13:43
 * SysUserMapper接口
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
