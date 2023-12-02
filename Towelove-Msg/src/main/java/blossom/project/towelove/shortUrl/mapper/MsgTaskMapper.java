package blossom.project.towelove.shortUrl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import blossom.project.towelove.shortUrl.entity.MsgTask;
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

    /**
     * 目前begin/endTime并不使用 作为预留点
     * @param beginTime 消息开始时间
     * @param endTime 消息结束时间
     * @param msgType 消息类型
     * @param total 集群机器总数量
     * @param index 当前机器编号
     * @return
     */
    List<MsgTask> selectAfterTenMinJob(@Param("beginTime") Time beginTime,
                                       @Param("endTime") Time endTime,
                                       @Param("msgType")Integer msgType,
                                       @Param("total")Integer total,
                                       @Param("index")Integer index);
}

