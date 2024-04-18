package blossom.project.towelove.server.mq.rocketmq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 张锦标
 * @date: 2024/4/18 5:21 PM
 * TemplateDTO类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemplateDTO {
    private Long id;
    private String test;
}
