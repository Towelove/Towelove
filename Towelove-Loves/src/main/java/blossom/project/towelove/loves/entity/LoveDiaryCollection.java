package blossom.project.towelove.loves.entity;

import blossom.project.towelove.common.domain.dto.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.loves.entity
 * @className: LoveDiaryCollection
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 15:33
 * @version: 1.0
 * 恋爱日记合集
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("love_diary_collection")
public class LoveDiaryCollection extends BaseEntity {

    @TableId
    private Long id;

    @TableField("couple_id")
    private Long coupleId;

    private String cover;

    private String title;


}
