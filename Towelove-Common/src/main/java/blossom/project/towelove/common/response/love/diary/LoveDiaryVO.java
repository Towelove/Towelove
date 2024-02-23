package blossom.project.towelove.common.response.love.diary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.response.love.diary
 * @className: LoveDiaryVO
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/23 14:58
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoveDiaryVO {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否同步
     */
    private boolean synchronous;

    /**
     * 图片集合
     */
    private List<DiaryImageDto> images;


    private LocalDateTime updateTime;
}
