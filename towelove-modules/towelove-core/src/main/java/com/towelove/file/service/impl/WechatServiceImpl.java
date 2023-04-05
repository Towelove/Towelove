package com.towelove.file.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.squareup.moshi.Moshi;
import com.towelove.common.core.constant.MsgTypeConstant;
import com.towelove.common.core.constant.WxConstant;
import com.towelove.file.domain.wx.ChatGPTRequest;
import com.towelove.file.domain.wx.ChatGptResponse;
import com.towelove.file.domain.wx.Messages;
import com.towelove.file.domain.wx.ReceiveMessage;
import com.towelove.file.domain.wx.ReplyMessage;
import com.towelove.file.domain.wx.WxToken;
import com.towelove.file.service.WechatService;
import com.towelove.file.util.XMLUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: 张锦标
 * @date: 2023/4/2 14:18
 */
@Service
public class WechatServiceImpl implements WechatService {
    Logger log = LoggerFactory.getLogger(WechatServiceImpl.class);

    @Value("${chatGPT.proxy-url}")
    private String PROXY_URL;
    @Value("${chatGPT.proxy-port}")
    private Integer PROXY_PORT;
    @Value("${chatGPT.api-url}")
    private String API_URL;
    @Value("${chatGPT.api-key}")
    private String API_KEY;
    // checkSignature 方法的实现省略

    @Override
    public String getAccessToken(String appId, String secret) {
        String url = WxConstant.GET_TOKEN.replace("APPID",
                appId).replace("APPSECRET", secret);
        //使用okHttp调用微信接口得到token
        //创建http客户端
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .callTimeout(10, TimeUnit.SECONDS)
                .build();
        //创建get请求体
        Request request = new Request.Builder()
                .url(url)
                .get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String tokenJson = response.body().string();
            WxToken wxToken = com.alibaba.fastjson.JSONObject
                    .parseObject(tokenJson, WxToken.class);
            log.info("wxToken======>"+wxToken.toString());
            return wxToken.getAccess_token();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getContentFromGpt(String content) {
        ChatGPTRequest chatGPTRequest = new ChatGPTRequest();
        chatGPTRequest.setModel("gpt-3.5-turbo-0301");
        chatGPTRequest.setMax_tokens(3072);
        chatGPTRequest.setTemperature(0);
        Messages messages1 = new Messages();
        messages1.setContent(content);
        messages1.setRole("user");
        Messages[] messages = {
                messages1
        };
        chatGPTRequest.setMessages(messages);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // 设置代理
//                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_URL, PROXY_PORT)))
                // 设置超时时间
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .callTimeout(600, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        Moshi moshi = new Moshi.Builder().build();
        String requestBody = moshi.adapter(ChatGPTRequest.class).toJson(chatGPTRequest);
        System.out.println("body："+requestBody);
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .post(okhttp3.RequestBody.create(mediaType, requestBody))
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String responseString = response.body().string();
            ChatGptResponse chatGptResponse = com.alibaba.fastjson.JSONObject.parseObject(responseString, ChatGptResponse.class);
            return chatGptResponse.getChoices()[0].getMessage().getContent();
        } catch (Exception e) {
            if (e instanceof NullPointerException){
                return "欢迎关注我们的公众号,在下方输入框可以直接输入问题哦";
            }
            e.printStackTrace();
            return "获得答案异常";
        }
    }
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