package com.towelove.system.domain.mail.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 邮件模版创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MailTemplateCreateReqVO extends MailTemplateBaseVO {

}
