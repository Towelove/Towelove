package blossom.project.towelove.msg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import blossom.project.towelove.msg.entity.MsgTask;
import org.apache.ibatis.annotations.Param;

import java.sql.Time;
import java.util.List;

/**
 * (MsgTask) 表数据库访问层
 *
 * @author 张锦标
 * @since 2023-11-21 19:33:06
 */
@Mapper
public interface MsgTaskMapper extends BaseMapper<MsgTask> {

    List<MsgTask> selectAfterTenMinJob(@Param("beginTime") Time beginTime,
                                       @Param("endTime") Time endTime,
                                       @Param("total")Integer total,
                                       @Param("index")Integer index);
}

