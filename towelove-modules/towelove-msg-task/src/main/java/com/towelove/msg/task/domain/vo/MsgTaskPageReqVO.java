package com.towelove.msg.task.domain.vo;


import com.towelove.common.core.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * @author: 张锦标
 * @date: 2023/3/11 17:32
 */
@Schema(description = "任务分页请求分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MsgTaskPageReqVO extends PageParam {

    @Schema(description = "邮箱", required = true, example = "460219753@qq.com")
    private String mail;

    @Schema(description = "用户名" , required = true , example = "zjb")
    private String username;

}