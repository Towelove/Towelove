package blossom.project.towelove.common.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author: ZhangBlossom
 * @date: 2024/2/3 12:21
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvitedEmailRequest {

    @Email
    private String email;

    @NotBlank(message = "content can not be null!!!")
    private String content;




}
