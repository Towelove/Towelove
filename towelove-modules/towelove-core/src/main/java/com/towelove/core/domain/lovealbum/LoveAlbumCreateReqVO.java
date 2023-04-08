package com.towelove.core.domain.lovealbum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author: 张锦标
 * @date: 2023/4/8 13:34
 * LoveAlbumCreateReqVO类
 */
@Schema(description = "恋爱相册创建VO Request")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class LoveAlbumCreateReqVO extends LoveAlbumBaseVO{
}
