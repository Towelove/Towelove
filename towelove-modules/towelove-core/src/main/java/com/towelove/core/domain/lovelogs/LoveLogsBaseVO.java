package com.towelove.core.domain.lovelogs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/4/8 14:06
 * LoveLogsBaseVO类
 */
@Data
public class LoveLogsBaseVO {
    @Schema(description = "今日描述", required = true, example = "描述一下")
    @NotNull(message = "今日描述必填")
    private String description;

    @Schema(description = "恋爱相册id", required = true, example = "1024")
    private String urls;

    @Schema(description = "是否他人可见", required = false, example = "true")
    private Integer canSee;


}
