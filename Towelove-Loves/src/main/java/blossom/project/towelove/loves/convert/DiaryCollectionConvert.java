package blossom.project.towelove.loves.convert;

import blossom.project.towelove.common.request.loves.diary.DiaryCollectionCreateRequest;
import blossom.project.towelove.common.response.love.diary.DiaryCollectionDTO;
import blossom.project.towelove.loves.entity.LoveDiaryCollection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.loves.convert
 * @className: DiaryCollectionConvert
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 16:43
 * @version: 1.0
 */
@Mapper
public interface DiaryCollectionConvert {
    DiaryCollectionConvert INSTANCE = Mappers.getMapper(DiaryCollectionConvert.class);

    DiaryCollectionDTO convert(LoveDiaryCollection loveDiaryCollection);

    List<DiaryCollectionDTO> convert(List<LoveDiaryCollection> loveDiaryCollections);

    LoveDiaryCollection convert(DiaryCollectionCreateRequest diaryCollectionCreateRequest);

    DiaryCollectionDTO convert2DTO(DiaryCollectionCreateRequest diaryCollectionDTO);


}
