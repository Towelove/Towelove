package com.towelove.file.controller;


import com.towelove.file.domain.ChatGPTRequest;
import com.towelove.file.service.impl.ChatGptService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.InetSocketAddress;
import java.net.Proxy;
import com.squareup.moshi.Moshi;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit;

/**
 * @author: 张锦标
 * @date: 2023/4/2 10:09
 * ChatGptController类
 */
@RestController
@RequestMapping("/chat/gpt")
public class ChatGptController {

    @Autowired
    ChatGptService chatGptService;

    public static final String path = "https://api.openai.com/v1/completions";

    @Value("${chatGPT.proxy-url}")
    private String PROXY_URL;
    @Value("${chatGPT.proxy-port}")
    private Integer PROXY_PORT;
    @Value("${chatGPT.api-url}")
    private String API_URL;
    @Value("${chatGPT.api-key}")
    private String API_KEY;

    @PostMapping("/chatGPTproxy")
    public String chatGPTproxy(@RequestBody ChatGPTRequest chatGPTRequest){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // 设置代理
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_URL, PROXY_PORT)))
                // 设置超时时间
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
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
            return responseString;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
