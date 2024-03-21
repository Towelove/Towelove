package blossom.project.towelove.loves.convert;

import blossom.project.towelove.common.request.loves.diary.DiaryCreateRequest;
import blossom.project.towelove.common.request.loves.diary.UpdateDiaryRequest;
import blossom.project.towelove.common.response.love.diary.LoveDiaryDTO;
import blossom.project.towelove.common.response.love.diary.LoveDiaryVO;
import blossom.project.towelove.loves.entity.LoveDiary;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.loves.convert
 * @className: DiaryConvert
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/21 15:36
 * @version: 1.0
 */
@Mapper
public interface DiaryConvert {
    DiaryConvert INSTANCE = Mappers.getMapper(DiaryConvert.class);

    LoveDiary convert(DiaryCreateRequest request);

    LoveDiaryDTO convert(LoveDiary loveDiary);

    LoveDiaryVO convert2Vo(LoveDiary loveDiary);

    LoveDiary convert(UpdateDiaryRequest updateDiaryRequest);
}
