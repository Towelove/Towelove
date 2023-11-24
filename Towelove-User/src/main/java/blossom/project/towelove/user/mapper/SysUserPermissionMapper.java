package blossom.project.towelove.user.mapper;

import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import blossom.project.towelove.user.domain.SysPermission;
import blossom.project.towelove.user.domain.SysUserPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserPermissionMapper extends BaseMapper<SysUserPermission> {

}
