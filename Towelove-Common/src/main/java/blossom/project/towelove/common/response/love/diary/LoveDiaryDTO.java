package blossom.project.towelove.common.response.love.diary;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.response.love.diary
 * @className: LoveDiaryDTO
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 17:59
 * @version: 1.0
 */
@Data
public class LoveDiaryDTO {

    private Long id;

    /**
     * 日记合集编号
     */
    private Long diaryCollectionId;
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
    private List<String> images;
}
