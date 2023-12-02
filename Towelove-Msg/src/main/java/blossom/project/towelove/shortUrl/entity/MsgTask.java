package blossom.project.towelove.shortUrl.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;
import java.util.Map;

import blossom.project.towelove.framework.mysql.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * (MsgTask) 表实体类
 *
 * @author 张锦标
 * @since 2023-11-21 19:33:06
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "msg_task",autoResultMap = true)
public class MsgTask extends BaseEntity {
    //主键
    @TableId(type = IdType.AUTO)
    private Long id;

    //用户id
    private Long userId;

    //邮箱账号id
    private Long accountId;

    //模板id
    private Long templateId;

    //接收消息的邮箱账号
    private String receiveAccount;

    //发送人名称
    private String nickname;

    //标题
    private String title;

    //内容
    private String content;

    //发送 日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sendDate;

    //发送 时间
    @JsonFormat(pattern = "HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime sendTime;

    //消息类型 0：发送一次 1：定时发送
    private Integer msgType;

    /**
     * 参数数组(自动根据内容生成)
     */
    @TableField(value = "params",typeHandler = JacksonTypeHandler.class)
    private List<String> params;

    @TableField(value = "json_map",typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> jsonMap;

}

