package com.towelove.core.domain.lovelogs;

import com.towelove.common.core.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author: 张锦标
 * @date: 2023/3/26 20:49
 * LoveAlbumPageReqVO类
 */
@Schema(description = "恋爱日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoveLogsPageReqVO extends PageParam {
    @Schema(description = "相册id", required = true, example = "1024")
    @NotNull(message = "恋爱相册Id必填")
    private String loveAlbumId;
}
