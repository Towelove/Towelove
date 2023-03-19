package com.towelove.system.domain.mail.vo.template;

import com.towelove.common.core.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 邮件模版分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MailTemplatePageReqVO extends PageParam {

    @Schema(description = "状态,参见 CommonStatusEnum 枚举", example = "1")
    private Integer status;

    @Schema(description = "标识,模糊匹配", example = "code_1024")
    private String code;

    @Schema(description = "名称,模糊匹配", example = "芋头")
    private String name;

    @Schema(description = "账号编号", example = "2048")
    private Long accountId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern =  "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime[] createTime;

}