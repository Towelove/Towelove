package blossom.project.towelove.common.response.surl;

import lombok.Data;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.request.surl
 * @className: CreateShortUrlRequest
 * @author: Link Ji
 * @description: TODO
 * @date: 2023/12/2 15:38
 * @version: 1.0
 */
@Data
public class CreateShortUrlResponse {

    /**
     * 需求业务
     */
    private String from;

    /**
     * 源URL
     */
    private String sourceUrl;

    /**
     * 是否需要统计
     */
    private boolean statistics;
}
