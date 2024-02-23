package blossom.project.towelove.loves.mapper;

import blossom.project.towelove.common.response.love.diary.DiaryImageDto;
import blossom.project.towelove.loves.entity.LoveDiary;
import blossom.project.towelove.loves.entity.LoveDiaryImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.loves.mapper
 * @className: LoveDiaryImageMapper
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/21 16:27
 * @version: 1.0
 */
@Mapper
public interface LoveDiaryImageMapper extends BaseMapper<LoveDiaryImage> {
    List<String> getDiaryImageByDiaryId(@Param("diaryId") Long diaryId);

    void insertBatch(List<LoveDiaryImage> loveDiaryImages);

    List<DiaryImageDto> getImageUrlByDiaryId(@Param("diaryId") Long diaryId);
}
