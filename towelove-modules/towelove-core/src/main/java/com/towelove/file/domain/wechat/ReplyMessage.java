package com.towelove.file.domain.wechat;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author: 张锦标
 * @date: 2023/4/2 15:08
 * ReplyMessage类
 */
@Data
@XmlRootElement(name="xml")
public class ReplyMessage {
    /**
     * 开发者微信号
     */
    private String ToUserName;
    /**
     * 发送方账号(一个openid）
     */
    private String FromUserName;
    /**
     * 消息创建时间（整形）
     */
    private String CreateTime;
    /**
     * 消息类型
     */
    private String MsgType;
    /**
     * 文本消息内容
     */
    private String Content;
}
