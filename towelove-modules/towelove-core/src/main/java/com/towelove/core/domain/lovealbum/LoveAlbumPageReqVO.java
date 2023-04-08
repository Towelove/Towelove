package com.towelove.core.domain.lovealbum;

import com.towelove.common.core.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author: 张锦标
 * @date: 2023/3/26 21:14
 * LoveAlbumPageReqVO类
 */
@Schema(description = "恋爱相册分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoveAlbumPageReqVO extends PageParam {
}
