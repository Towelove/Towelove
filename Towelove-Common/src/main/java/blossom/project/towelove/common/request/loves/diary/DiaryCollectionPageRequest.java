package blossom.project.towelove.common.request.loves.diary;

import blossom.project.towelove.common.page.PageRequest;
import lombok.Data;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.request.loves.diary
 * @className: LoveDiaryCollectionPageRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 16:30
 * @version: 1.0
 */
@Data
public class DiaryCollectionPageRequest extends PageRequest {

    private Long coupleId;

}
