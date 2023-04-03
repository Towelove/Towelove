package com.towelove.file.controller;


import cn.hutool.http.server.HttpServerResponse;
import com.squareup.moshi.Moshi;
import com.towelove.file.domain.ChatGPTRequest;
import com.towelove.file.domain.Messages;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String chatGPTproxy(
            HttpServletResponse response,
            HttpServletRequest request,
            @RequestBody String requestBody, @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
            @RequestParam(name = "encrypt_type", required = false) String encType,
            @RequestParam(name = "msg_signature", required = false) String msgSignature) {

        System.out.println("requestbody:----"+requestBody);
        System.out.println("signature:----"+signature);
        System.out.println("timestamp:----"+timestamp);
        System.out.println("nonce:----"+nonce);
        System.out.println("encrypt_type:----"+encType);
        System.out.println("msg_signature:----"+msgSignature);
        return requestBody;
        //OkHttpClient okHttpClient = new OkHttpClient.Builder()
        //        // 设置代理
        //        .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_URL, PROXY_PORT)))
        //        // 设置超时时间
        //        .connectTimeout(10, TimeUnit.SECONDS)
        //        .writeTimeout(10, TimeUnit.SECONDS)
        //        .readTimeout(30, TimeUnit.SECONDS)
        //        .callTimeout(60, TimeUnit.SECONDS)
        //        .build();
        //MediaType mediaType = MediaType.parse("application/json");
        //Moshi moshi = new Moshi.Builder().build();
        //ChatGPTRequest chatGPTRequest = new ChatGPTRequest();
        //Messages[] messages = new Messages[0];
        //messages[0].setContent("Redis是什么？");
        //chatGPTRequest.setMessages(messages);
        //String chatRequest = moshi.adapter
        //        (ChatGPTRequest.class).toJson(chatGPTRequest);
        //System.out.println("body：" + requestBody);
        //Request request1 = new Request.Builder()
        //        .url(API_URL)
        //        .addHeader("Content-Type", "application/json")
        //        .header("Authorization", "Bearer " + API_KEY)
        //        .post(okhttp3.RequestBody.create(mediaType, requestBody))
        //        .build();
        //try {
        //    Response response1 = okHttpClient.newCall(request1).execute();
        //    String responseString = response1.body().string();
        //    return responseString;
        //} catch (Exception e) {
        //    e.printStackTrace();
        //    return "";
        //}
    }
}
