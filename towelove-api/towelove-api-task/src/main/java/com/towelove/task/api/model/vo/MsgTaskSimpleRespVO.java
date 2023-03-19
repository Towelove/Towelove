package com.towelove.task.api.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Schema(description = "消息编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "用户编号", required = true, example = "1024")
    private Long userId;

    @Schema(description = "邮件账号编号", required = true, example = "1024")
    private Long accountId;

    @Schema(description = "模板编号", required = true, example = "1024")
    private Long templateId;

    @Schema(description = "接收者的邮箱",required = true)
    private String receiveAccount;

    @Schema(description = "发送邮件的时间",required = true)
    private Date sendTime;


}