package com.towelove.core.domain.lovelogs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author: 张锦标
 * @date: 2023/4/8 14:03
 * LoveLogsUpdateReqVO类
 */
@Schema(description = "恋爱日志修改VO Request")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class LoveLogsUpdateReqVO extends LoveLogsBaseVO{
    @Schema(description = "恋爱相册id", required = true, example = "1024")
    @NotNull(message = "恋爱相册id必填")
    private Long loveAlbumId;

    @Schema(description = "恋爱日志id", required = true, example = "1024")
    @NotNull(message = "恋爱日志id必填")
    private Long id;
}
