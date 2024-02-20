package blossom.project.towelove.loves.entity;

import blossom.project.towelove.common.domain.dto.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.loves.entity
 * @className: LoveDiary
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 15:35
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("love_diary")
public class LoveDiary extends BaseEntity {
    @TableId
    private Long id;

    /**
     * 日记合集编号
     */
    private Long diary_id;

    private String title;

    private String content;

    /**
     * 是否同步
     */
    private boolean synchronous;
}
