package blossom.project.towelove.user.mapper;

import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import blossom.project.towelove.user.domain.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    List<SysUserPermissionDto> selectUserPermissionByUserId(@Param("userId") Long userId);
}
