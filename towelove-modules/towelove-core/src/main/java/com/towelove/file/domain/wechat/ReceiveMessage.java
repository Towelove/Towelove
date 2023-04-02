package com.towelove.file.domain.wechat;

import lombok.Data;


/**
 * @author: 张锦标
 * @date: 2023/4/2 15:03
 * ReceiveMessage类
 */
@Data
public class ReceiveMessage {
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
    /**
     * 消息ID 64位
     */
    String MsgId;
    /**
     * 消息的数据ID 消息来自文章才有
     */
    private String MsgDataId;
    /**
     * 多图文时第几篇文章，从1开始 消息如果来自文章才有
     */
    private String Idx;
    /**
     * 订阅事件 subscribe订阅 unsbscribe取消订阅
     */
    private String Event;
}
