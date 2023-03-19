package com.towelove.msg.task.domain.vo;


import com.towelove.common.core.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:32
 */
@Schema(description = "任务分页请求分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MsgTaskPageReqVO extends PageParam {

    @Schema(description = "标题", required = true, example = "你好")
    private String title;

    @Schema(description = "发送人", required = true, example = "张锦标")
    private String nickname;

    @Schema(description = "发送时间" , required = true , example = "2022-11-06 22:20:20")
    private Date sendTime;

    @Schema(description = "内容" , required = true , example = "早安")
    private String content;

}