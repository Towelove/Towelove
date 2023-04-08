package com.towelove.core.domain.lovelogs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author: 张锦标
 * @date: 2023/4/8 14:03
 * LoveLogsCreateReqVO类
 */
@Schema(description = "恋爱日志创建VO Request")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class LoveLogsCreateReqVO extends LoveLogsBaseVO{
    @Schema(description = "恋爱相册id", required = false, example = "1024")
    private Long loveAlbumId;
}
