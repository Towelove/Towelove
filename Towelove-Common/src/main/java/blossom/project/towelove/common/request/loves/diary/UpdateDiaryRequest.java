package blossom.project.towelove.common.request.loves.diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.request.loves.diary
 * @className: UpdateDiaryRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/20 22:04
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDiaryRequest {

    @NotNull(message = "id could not be null")
    private Long id;

    private String title;

    private String content;

    private Boolean synchronous = false;

    private List<DiaryImageRequest> images;

}
