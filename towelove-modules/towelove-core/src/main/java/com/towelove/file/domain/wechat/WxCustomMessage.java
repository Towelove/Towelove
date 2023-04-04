package com.towelove.file.domain.wechat;

import lombok.Data;

@Data
public class WxCustomMessage {
    /**
     * 用户的openId
     */
    private String touser;
    /**
     * 消息类型
     */
    private String msgtype;
    /**
     * 消息文本：{
     *     如果是文本消息 key == content
     * }
     */
    private Text text;
}
