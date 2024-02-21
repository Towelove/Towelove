package blossom.project.towelove.common.request.loves.diary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.request.loves.diary
 * @className: LoveDiaryCreateRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/21 0:01
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryCreateRequest {
    /**
     * 日记合集编号
     */
    @NotNull(message = "diaryCollectionId could not be null")
    private Long diaryCollectionId;

    @NotBlank(message = "title could not be null")
    private String title;

    @NotBlank(message = "content could not be null")
    private String content;

    /**
     * 是否同步
     */
    private boolean synchronous = false;

    private List<String> images;
}
