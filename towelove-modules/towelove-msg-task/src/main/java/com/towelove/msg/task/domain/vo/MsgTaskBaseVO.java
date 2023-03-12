package com.towelove.msg.task.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:32
 */
@Data
public class MsgTaskBaseVO {

    @Schema(description = "发送人名称", required = false, example = "zjb")
    private String nickname;

    @Schema(description = "标题", required = true, example = "早上好")
    @NotNull(message = "标题必填")
    private String title;

    @Schema(description = "消息内容", required = true, example = "你好呀")
    @NotNull(message = "消息内容不能为空")
    private String content;

    @Schema(description = "发送时间", required = true, example = "2002-03-22 12:12:12")
    @NotNull(message = "发送时间不能为空")
    private Date sendTime;


}