package blossom.project.towelove.server.mapper;

import blossom.project.towelove.server.dto.NoticeVO;
import blossom.project.towelove.server.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.mapper
 * @className: NoticeMapper
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 22:15
 * @version: 1.0
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    List<NoticeVO> selectNoticeByUserId(@Param("userId") Long userId);
}
