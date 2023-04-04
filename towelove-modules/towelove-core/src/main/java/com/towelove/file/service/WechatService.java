package com.towelove.file.service;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.alibaba.fastjson2.JSONObject;
import com.towelove.file.domain.wechat.ReceiveMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author: 张锦标
 * @date: 2023/4/2 14:10
 * WechatService类
 */

public interface WechatService {
    Map<String, String> parseXmlData2Map(HttpServletRequest req);
    JSONObject orderReply(HttpServletResponse response, ReceiveMessage receiveMessage) throws Exception;
    String getAccessToken(String appId,String secret);

    String getContentFromGpt(String content);
}
