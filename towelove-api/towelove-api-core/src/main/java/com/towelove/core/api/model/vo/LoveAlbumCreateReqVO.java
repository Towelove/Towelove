package com.towelove.core.api.model.vo;

import com.towelove.core.api.model.LoveAlbumBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "恋爱相册创建VO Request")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class LoveAlbumCreateReqVO extends LoveAlbumBaseVO {
}
