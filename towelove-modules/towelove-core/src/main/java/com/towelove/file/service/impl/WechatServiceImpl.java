package com.towelove.file.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.towelove.common.core.constant.MsgTypeConstant;
import com.towelove.common.core.constant.WechatMsgTypeConstant;
import com.towelove.file.domain.wechat.ReceiveMessage;
import com.towelove.file.domain.wechat.ReplyMessage;
import com.towelove.file.service.TextReplyService;
import com.towelove.file.service.WechatService;
import com.towelove.file.util.XMLUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.reflections.Reflections.log;

/**
 * @author: 张锦标
 * @date: 2023/4/2 14:18
 */
@Service
public class WechatServiceImpl implements WechatService {

    // checkSignature 方法的实现省略

    /**
     * 用户在微信公众号发送消息后, 微信服务器会发送一个 POST 请求过来
     * POST 请求中携带者用户发送的数据（XML 格式）
     *
     * @param req 可通过该参数获得 XML 数据包的输入流
     * @return 数据的 Map 格式
     */
    @Override
    public Map<String, String> parseXmlData2Map(HttpServletRequest req) {
        HashMap<String, String> msgMap = new HashMap<>();

        try {
            ServletInputStream inputStream = req.getInputStream();

            // dom4j 用于读取 XML 文件输入流的类
            SAXReader saxReader = new SAXReader();
            // 读取 XML 文件输入流, XML 文档对象
            Document document = saxReader.read(inputStream);
            // XML 文件的根节点
            Element root = document.getRootElement();
            // 所有的子节点
            List<Element> childrenElement = root.elements();
            for (Element element : childrenElement) {
                msgMap.put(element.getName(), element.getStringValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgMap;
    }

    @Override
    public JSONObject orderReply(HttpServletResponse response, ReceiveMessage receiveMessage) throws Exception {
        JSONObject mav = new JSONObject();
        if (receiveMessage.getMsgType().equals(MsgTypeConstant.Event) && receiveMessage.getEvent().equals("subscribe")) {
            receiveMessage.setContent("关注");
        }
        ReplyMessage replyMessage = new ReplyMessage();
        replyMessage.setMsgType(MsgTypeConstant.TEXT);
        replyMessage.setFromUserName(receiveMessage.getToUserName());
        replyMessage.setToUserName(receiveMessage.getFromUserName());
        replyMessage.setCreateTime(String.valueOf(System.currentTimeMillis()));
        replyMessage.setContent("感谢您的关注，爱你哦");
        String msg = XMLUtil.ObjToXml(replyMessage);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(msg);
        mav.put("msg", "操作成功");
        return mav;
    }
}