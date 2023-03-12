package com.towelove.msg.task.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * @author: 张锦标
 * @date: 2023/3/11 17:32
 */
@Schema(description = "消息任务创建VO Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MsgTaskCreateReqVO extends MsgTaskBaseVO {

}