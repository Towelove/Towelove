package com.towelove.msg.task.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:32
 */
@Data
public class MsgTaskBaseVO {
    @Schema(description = "用户id", required = true, example = "1024")
    @NotNull(message = "用户ID必填")
    private Long userId;

    @Schema(description = "当前用户账号id", required = true, example = "1024")
    @NotNull(message = "账号id必填")
    private Long accountId;

    @Schema(description = "模板id", required = true, example = "1024")
    private Long templateId;

    @Schema(description = "发送人名称", required = false, example = "zjb")
    private String nickname;

    @Schema(description = "标题", required = true, example = "早上好")
    @NotNull(message = "标题必填")
    private String title;

    @Schema(description = "消息内容", required = true, example = "你好呀")
    @NotNull(message = "消息内容不能为空")
    private String content;

    @Schema(description = "接收人邮箱", required = true, example = "460219753@qq.com")
    @NotNull(message = "接收人邮箱不能为空")
    private String receiveAccount;

    @Schema(description = "发送时间", required = true, example = "2002-03-22 12:12:12")
    @NotNull(message = "发送时间不能为空")
    private Date sendTime;
}