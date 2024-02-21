package blossom.project.towelove.common.response.love.diary;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.response.love.diary
 * @className: DiaryTitleDTO
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 22:49
 * @version: 1.0
 */
@Data
public class DiaryTitleDTO {
    private Long id;

    private String title;

    private LocalDateTime createTime;
}
