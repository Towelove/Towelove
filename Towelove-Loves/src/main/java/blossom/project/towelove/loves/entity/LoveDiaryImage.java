package blossom.project.towelove.loves.entity;

import blossom.project.towelove.common.domain.dto.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.loves.entity
 * @className: LoveDiaryImage
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/21 15:53
 * @version: 1.0
 */
@TableName("love_diary_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoveDiaryImage extends BaseEntity {
    private Long id;

    @TableField("diary_id")
    private Long diaryId;

    private String url;
}


