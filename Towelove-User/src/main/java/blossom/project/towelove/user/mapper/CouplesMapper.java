package blossom.project.towelove.user.mapper;

import blossom.project.towelove.common.response.user.CouplesRespDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import blossom.project.towelove.user.entity.Couples;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2024-01-17 13:36:03
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 
 */
@Mapper
public interface CouplesMapper extends BaseMapper<Couples> {

    public List<Long> selectAllByBoyIdAndGirlIdLongs(@Param("boyId") Long boyId, @Param("girlId") Long girlId);

    CouplesRespDTO selectCoupleIdByUserId(@Param("userId") Long userId,@Param("sex") String sex);
}



