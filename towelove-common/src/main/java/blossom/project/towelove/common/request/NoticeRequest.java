package blossom.project.towelove.common.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.request
 * @className: NoticeRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 22:24
 * @version: 1.0
 */
@Data
public class NoticeRequest {
    /**
     * 通知内容
     */
    private String content;

    /**
     * 系统通知类型默认为全体
     */
    private Integer type = 0;

}
