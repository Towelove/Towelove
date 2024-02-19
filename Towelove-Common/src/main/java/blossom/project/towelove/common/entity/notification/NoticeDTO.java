package blossom.project.towelove.common.entity.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.entity.notification
 * @className: notification
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 19:22
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO {

    private Long userId;

    private String content;

}
