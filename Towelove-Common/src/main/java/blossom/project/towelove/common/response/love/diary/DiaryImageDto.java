package blossom.project.towelove.common.response.love.diary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.response.love.diary
 * @className: DiaryImageDto
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/23 15:02
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryImageDto {
    private Long id;

    private String url;

    private LocalDateTime createTime;
}
