package com.towelove.system.domain.mail.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Map;
/**

 * @author 张锦标
 * @date: 2023/3/8 20:10
 */
@Schema(description = "RPC 服务 - 邮件发送给 Admin 或者 Member 用户 Request DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailSendSingleToUserReqDTO {

    @Schema(description = "用户编号", example = "1024")
    private Long userId;
    @Schema(description = "邮箱号", required = true, example = "460219753@qq.com")
    @Email
    private String mail;

    @Schema(description = "邮件模板编号", required = true, example = "USER_SEND")
    @NotNull(message = "邮件模板编号不能为空")
    private String templateCode;

    @Schema(description = "邮件模板参数")
    private Map<String, Object> templateParams;

}