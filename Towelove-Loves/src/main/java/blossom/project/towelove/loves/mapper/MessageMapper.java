package blossom.project.towelove.loves.mapper;

import blossom.project.towelove.loves.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 留言表 Mapper 接口
 *
 * @Author 苏佳
 * @Date 2024 01 17
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
