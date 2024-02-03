package blossom.project.towelove.common.request.todoList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author: ZhangBlossom
 * @date: 2024/1/24 12:51
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@AllArgsConstructor
@Builder
@Data
public class TodoRemindRequest {


    /**
     * 接收方email
     */
    @Email
    private String email;

    /**
     * 接收的消息
     */
    @NotBlank(message = "the content can not be null!!!")
    private String content;

    /**
     * 提醒时间
     */
    @NotNull(message = "the remindTime can not be null!!!")
    private LocalDateTime remindTime;
}
