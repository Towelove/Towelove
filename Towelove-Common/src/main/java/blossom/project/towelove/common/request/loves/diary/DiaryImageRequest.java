package blossom.project.towelove.common.request.loves.diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.request.loves.diary
 * @className: DiaryImageRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/21 20:39
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryImageRequest {

    private String url;

    private Position position;
}
