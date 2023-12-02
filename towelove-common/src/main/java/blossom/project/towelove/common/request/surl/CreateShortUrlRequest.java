package blossom.project.towelove.common.request.surl;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

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
public class CreateShortUrlRequest {

    /**
     * 需求微服务
     */
    private String from;

    /**
     * 源URL，格式：
     * https://xxxx:xxx/xxxx/xxx
     */
    @NotBlank(message = "source url could not be null")
    private String sourceUrl;

    /**
     * 是否需要统计
     */
    //TODO 这里肯定默认true or false了
    @NotNull(message = "statistics could not be null")
    private Boolean statistics;
}
