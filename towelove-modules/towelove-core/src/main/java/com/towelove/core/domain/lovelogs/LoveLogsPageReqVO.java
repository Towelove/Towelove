package com.towelove.core.domain.lovelogs;

import com.towelove.common.core.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
}
