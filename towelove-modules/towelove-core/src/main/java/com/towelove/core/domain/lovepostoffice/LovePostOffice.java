package com.towelove.core.domain.lovepostoffice;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/5/4 17:10
 * LovePostOffice类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("love_post_office")
public class LovePostOffice {
    @TableId
    private Long id;
    @NotNull
    private Long senderId;

    @NotNull
    private String senderName;
    @NotNull
    private Long receiverId;
    @NotNull
    private String content;
    private Date sendTime;
    private Boolean isRead;
    @TableLogic(value = "false", delval = "true")
    private Boolean deleted;

}
