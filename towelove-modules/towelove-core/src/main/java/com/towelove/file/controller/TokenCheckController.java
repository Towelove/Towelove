package com.towelove.file.controller;


import com.squareup.moshi.Moshi;
import com.towelove.file.domain.ChatGPTRequest;
import com.towelove.file.domain.wechat.ReceiveMessage;
import com.towelove.file.service.WechatService;
import com.towelove.file.service.impl.ChatGptService;
import com.towelove.file.util.TokenCheckUtil;
import com.towelove.file.util.XMLUtil;
import lombok.extern.java.Log;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;


/**
 * @author: 张锦标
 * @date: 2023/4/2 14:58
 * TokenCheckController类
 */
@Log
@RestController
@RequestMapping("/wx")
public class TokenCheckController {
    @Value("${wechat.mp.token}")
    private String token;
    @Autowired
    private WechatService wechatService;
    @Autowired
    ChatGptService chatGptService;

    public static final String path = "https://openapi.capoo.cc/v1/completions"; //无需代理
    //public static final String path = "https://openapi.deno.capoo.cc/v1/completions";
    //public static final String path = "https://openapi.cf.capoo.cc/v1/completions";
    //public static final String path = "https://api.openai.com/v1/completions"; //必须代理
    @Value("${chatGPT.proxy-url}")
    private String PROXY_URL;
    @Value("${chatGPT.proxy-port}")
    private Integer PROXY_PORT;
    @Value("${chatGPT.api-url}")
    private String API_URL;
    @Value("${chatGPT.api-key}")
    private String API_KEY;

    @GetMapping("/")
    public String index(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String echostr = TokenCheckUtil.checkToken(request, token);
        return echostr;
    }

    @PostMapping("/")
    public String chatGPTproxy(HttpServletResponse response, HttpServletRequest request,
                               @RequestBody String requestBody, @RequestParam("signature") String signature,
                               @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
                               @RequestParam(name = "encrypt_type", required = false) String encType,
                               @RequestParam(name = "msg_signature", required = false) String msgSignature) throws IOException {

        ReceiveMessage receiveMessage = XMLUtil.parseXmlToMsg(requestBody);
        System.out.println("接收到的微信公众号的消息:" + receiveMessage);

        //ChatGPTRequest chatGPTRequest = new ChatGPTRequest();
        //chatGPTRequest.setMax_tokens(3072);
        ////封装okhttp请求向chatgpt发送请求
        //OkHttpClient okHttpClient = new OkHttpClient.Builder()
        //        // 设置代理
        //        .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_URL, PROXY_PORT)))
        //        // 设置超时时间
        //        .connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(30,
        //                TimeUnit.SECONDS).callTimeout(60, TimeUnit.SECONDS).build();
        //
        //MediaType mediaType = MediaType.parse("application/json");
        //Moshi moshi = new Moshi.Builder().build();
        //String chatGptRequestJson = moshi.adapter(ChatGPTRequest.class).toJson(chatGPTRequest);
        //System.out.println("向chatgpt发送的请求如下：" + chatGptRequestJson);
        //Request toChatGPTRequest = new Request.Builder().url(API_URL)
        //        .addHeader("Content-Type", "application/json")
        //        .header("Authorization", "Bearer " + API_KEY)
        //        .post(okhttp3.RequestBody.create
        //                (mediaType, chatGptRequestJson))
        //        .build();
        //
        //Response fromChatGPTResponse = okHttpClient.newCall(toChatGPTRequest).execute();
        //String chatGptResponseString = fromChatGPTResponse.body().string();
        //
        //receiveMessage.setContent(chatGptResponseString);

        return XMLUtil.parseMsgToXML(receiveMessage);
    }

    private String parseMsgToXML(ReceiveMessage receiveMessage) {
        // 创建document对象
        Document document = DocumentHelper.createDocument();
        // 创建根节点bookRoot
        Element xml = document.addElement("xml");
        // 向根节点中添加第一个节点
        Element toUserName = xml.addElement("ToUserName");
        // 向子节点中添加属性
        toUserName.addCDATA(receiveMessage.getToUserName());
        Element fromUserName = xml.addElement("FromUserName");
        fromUserName.addCDATA(receiveMessage.getFromUserName());
        Element createTime = xml.addElement("CreateTime");
        createTime.addCDATA(String.valueOf(System.currentTimeMillis()));
        Element msgType = xml.addElement("MsgType");
        msgType.addCDATA(receiveMessage.getMsgType());
        Element content = xml.addElement("Content");
        //这里的content内容应该是你从数据库或者某种方式生成的 而不是固定的
        content.addCDATA("hello呀，我是张锦标");
        String asXML = document.getRootElement().asXML();
        System.out.println(asXML);
        return asXML;

    }

    public static void main(String[] args) {
        try {
            // 创建document对象
            Document document = DocumentHelper.createDocument();
            // 创建根节点bookRoot
            Element xml = document.addElement("xml");
            // 向根节点中添加第一个节点
            Element toUserName = xml.addElement("ToUserName");
            // 向子节点中添加属性
            toUserName.addCDATA("oy__X6JbTiLxEVG85ABtAawsc_qw");
            Element fromUserName = xml.addElement("FromUserName");
            fromUserName.addCDATA("gh_71a0837d69a6");
            Element createTime = xml.addElement("CreateTime");
            createTime.addCDATA(String.valueOf(System.currentTimeMillis()));
            Element msgType = xml.addElement("MsgType");
            msgType.addCDATA("text");
            Element content = xml.addElement("Content");
            content.addCDATA("hello呀，我是张锦标");


            System.out.println(document.getRootElement().asXML());

            // 设置生成xml的格式
            OutputFormat of = OutputFormat.createPrettyPrint();
            // 设置编码格式
            of.setEncoding("UTF-8");
            // 生成xml文件
            File file = new File("D:\\desktop\\student.xml");
            if (file.exists()) {
                file.delete();
            }
            //创建一个xml文档编辑器
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), of);
            //把刚刚创建的document放到文档编辑器中
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
