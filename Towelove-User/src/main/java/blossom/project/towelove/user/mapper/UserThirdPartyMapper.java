package blossom.project.towelove.user.mapper;

/**
 * @Author SIK
 * @Date 2023 12 05 11 25
 **/

import blossom.project.towelove.user.domain.UserThirdParty;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserThirdPartyMapper extends BaseMapper<UserThirdParty> {
    @Select("SELECT * FROM user_third_party WHERE user_id = #{userId}")
    List<UserThirdParty> selectByUserId(Long userId);

    @Select("SELECT * FROM user_third_party WHERE third_party_id = #{third_party_id}")
    UserThirdParty selectByThridPartyId(String thridPartyId);
}

