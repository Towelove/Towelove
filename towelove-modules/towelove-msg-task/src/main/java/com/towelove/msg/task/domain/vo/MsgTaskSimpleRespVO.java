package com.towelove.msg.task.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:32
 */
@Schema(description = "消息的精简 Response VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgTaskSimpleRespVO {

    @Schema(description = "消息编号", required = false, example = "1024")
    private Long id;

    @Schema(description = "消息内容", required = false, example = "消息内容")
    private String content;

    @Schema(description = "接收者的邮箱", required = false
    )
    private String receiveAccount;

    @Schema(description = "发送邮件的时间", required = false)
    private Time sendTime;

    @Schema(description = "发送邮件的时间", required = false)
    private String title;


}