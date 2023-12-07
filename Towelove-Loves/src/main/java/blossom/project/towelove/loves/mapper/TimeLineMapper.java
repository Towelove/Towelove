package blossom.project.towelove.loves.mapper;

/**
 * @Author 苏佳
 * @Date 2023 12 01 17 08
 **/


import blossom.project.towelove.loves.entity.TimeLine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Timeline数据库访问层
 *
 * @Author 苏佳
 * @Date 2023 12 01 17 08
 */
@Mapper
public interface TimeLineMapper extends BaseMapper<TimeLine> {

}