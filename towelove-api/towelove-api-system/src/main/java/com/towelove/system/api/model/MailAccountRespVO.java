package com.towelove.system.api.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/3/16 9:27
 * MailAccountRespVO类
 */
@Schema(description = "管理后台 - 邮箱账号 Response VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MailAccountRespVO extends MailAccountBaseVO {

    @Schema(description = "编号", required = true, example = "1024")
    @NotNull(message = "编号不能为空")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private Date createTime;

}