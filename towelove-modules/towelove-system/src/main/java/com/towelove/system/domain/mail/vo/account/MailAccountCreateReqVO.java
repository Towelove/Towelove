package com.towelove.system.domain.mail.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 邮箱账号创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MailAccountCreateReqVO extends MailAccountBaseVO {

}
