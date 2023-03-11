package com.towelove.msg.task.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
 * @author: 张锦标
 * @date: 2023/3/11 17:32
 */
@Schema(description = "管理后台 - 邮箱账号的精简 Response VO")
@Data
public class MsgTaskSimpleRespVO {

    @Schema(description = "邮箱编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "邮箱", required = true, example = "768541388@qq.com")
    private String mail;

}