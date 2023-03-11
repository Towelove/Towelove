package com.towelove.msg.task.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:40
 * MsgTaskRespVO类
 * vo中只需要定义返回给网页的时候
 * 网页需要的参数即可
 */
@Schema(description = "消息任务响应VO Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MsgTaskRespVO extends MsgTaskBaseVO {
    @Schema(description = "编号", required = true, example = "1024")
    @NotNull(message = "编号不能为空")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;
}
