package blossom.project.towelove.common.request.msg;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgTaskCreateRequest {

    //用户id
    @NotNull(message = "userId can not be null!")
    private Long userId;

    //邮箱账号id
    @NotNull(message = "accountId can not be null!")
    private Long accountId;

    //模板id
    private Long templateId;

    //接收消息的邮箱账号
    @NotBlank(message = "receiveAccount can not be empty!")
    private String receiveAccount;

    //发送人名称
    private String nickname;

    //标题
    @NotBlank(message = "title can not be empty!")
    private String title;

    //内容
    @NotBlank(message = "content can not be empty!")
    private String content;

    //发送日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate sendDate;

    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime sendTime;

    //消息类型 0：发送一次 1：定时发送
    private Integer msgType;

    /**
     * 参数数组(自动根据内容生成)
     */
    private List<String> params;

    private Map<String, Object> jsonMap;
}

