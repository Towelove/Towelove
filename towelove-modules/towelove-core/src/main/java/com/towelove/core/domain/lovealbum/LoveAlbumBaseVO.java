package com.towelove.core.domain.lovealbum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/4/8 13:35
 * LoveAlbumBaseVO类
 */
@Data
public class LoveAlbumBaseVO {
    @Schema(description = "男生id", required = true, example = "1024")
    @NotNull(message = "男生ID必填")
    private Long boyId;

    @Schema(description = "女生id", required = true, example = "1024")
    @NotNull(message = "女生id必填")
    private Long girlId;

    @Schema(description = "相册标题名称", required = false, example = "我和她在一起啦")
    private String title;

    @Schema(description = "在一起的时间", required = true, example = "2002-03-22 12:12:12")
    @NotNull(message = "在一起的时间不能为空")
    private Date daysInLove;
}
