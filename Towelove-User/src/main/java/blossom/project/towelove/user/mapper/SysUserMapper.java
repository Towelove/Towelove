package blossom.project.towelove.user.mapper;


import blossom.project.towelove.common.response.user.CouplesInfoDto;
import blossom.project.towelove.common.response.user.CouplesRespDTO;
import blossom.project.towelove.common.response.user.LoginUserResponse;
import blossom.project.towelove.user.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser selectByPhoneNumberOrEmail(@Param("phone") String phone,@Param("email") String email);

    SysUser selectByThirdPartyId(@Param("socialUid") String socialUid);



    LoginUserResponse selectUserWithPermission(@Param("id") Long id);

    CouplesInfoDto selectCouplesInfo(@Param("userId") Long couplesUserId);
}
