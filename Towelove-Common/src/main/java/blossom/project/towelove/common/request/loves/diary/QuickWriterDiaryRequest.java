package blossom.project.towelove.common.request.loves.diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.request.loves.diary
 * @className: QuickWriterDiaryRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/23 15:16
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuickWriterDiaryRequest {

    private String content;
}
