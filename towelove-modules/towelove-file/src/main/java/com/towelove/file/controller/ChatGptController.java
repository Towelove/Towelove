package com.towelove.file.controller;


import com.alibaba.fastjson2.JSONObject;
import com.towelove.common.core.domain.R;
import com.towelove.file.service.impl.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;

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

    /**
     * 获取chatGPT返回的数据
     *
     * @param question
     * @return
     */
    @PostMapping("/chatGPT")
    public R chatGPT(@RequestParam String question) {

        //String proxyHost = "127.0.0.1";
        //String proxyPort = "7890";
        //// 对http开启代理
        //System.setProperty("http.proxyHost", proxyHost);
        //System.setProperty("http.proxyPort", proxyPort);
        //// 对https也开启代理
        //System.setProperty("https.proxyHost", proxyHost);
        //System.setProperty("https.proxyPort", proxyPort);


        // 构建你的请求头
        //HttpHeaders headers = new HttpHeaders();
        //headers.set("Content-Type", "application/json");
        //headers.set("Authorization", "Bearer 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");
        //
        ////设置请求参数
        //MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        //paramsMap.set("model", "text-davinci-003");
        //paramsMap.set("prompt", question);
        //paramsMap.set("max_tokens", "2048");
        //paramsMap.set("temperature", "0");
        //UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(path);
        //URI uri = builder.queryParams(paramsMap).build().encode().toUri();
        //
        //// 构建你的请求体参数
        //HashMap<String, String> map = new HashMap<>();
        //
        //// 组合请求头与请求体参数
        //HttpEntity<String> requestEntity =
        //        new HttpEntity<>(JSONObject.toJSONString(map), headers);
        //// path -> 请求地址，requestEntity -> 请求参数相关对象，HashMap.class -> 返回结果映射类型
        //return R.ok(restTemplate.postForObject(uri, requestEntity, R.class));
        return R.ok(chatGptService.postChatGPT(question));
    }

}
