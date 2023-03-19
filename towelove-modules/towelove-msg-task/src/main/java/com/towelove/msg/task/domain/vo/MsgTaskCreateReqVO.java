package com.towelove.msg.task.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:32
 */
@Schema(description = "消息任务创建VO Request VO")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class MsgTaskCreateReqVO extends MsgTaskBaseVO {
    @Schema(description = "创建任务的id", required = false, example = "1024")
    private Long id;
}
