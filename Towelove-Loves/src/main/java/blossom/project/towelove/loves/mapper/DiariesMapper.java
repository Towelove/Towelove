package blossom.project.towelove.loves.mapper;

import blossom.project.towelove.common.response.love.diary.DiaryImageDto;
import blossom.project.towelove.loves.entity.LoveDiaryCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.loves.mapper
 * @className: DiariesMapper
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 15:54
 * @version: 1.0
 */
@Mapper
public interface DiariesMapper extends BaseMapper<LoveDiaryCollection> {

    Long findQuickWriteCollection(@Param("userId") Long userId, @Param("title") String quickWriteDiaryTitle);
}
