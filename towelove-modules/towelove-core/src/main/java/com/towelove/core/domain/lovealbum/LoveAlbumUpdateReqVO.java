package com.towelove.core.domain.lovealbum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @author: 张锦标
 * @date: 2023/4/8 13:55
 * LoveAlbumUpdateReqVO类
 */
@Schema(description = "恋爱相册修改 Request VO")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class LoveAlbumUpdateReqVO extends LoveAlbumBaseVO{

    @Schema(description = "恋爱相册id", required = true, example = "1024")
    @NotNull(message = "恋爱相册id必填")
    private Long id;
}
