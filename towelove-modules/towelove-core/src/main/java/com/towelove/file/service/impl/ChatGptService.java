package com.towelove.file.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ChatGptService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatGptService.class);
    /**
     * 获取chatGPT返回的数据     * @param question     * @return
     */
    public String getResultFromChatGPT(String question) {        // 调用API接口
        String returnContent = postChatGPT(question);
        LOGGER.info("returnContent = " + returnContent);        // 解析返回的数据
        JSONObject returnContentObject = JSONObject.parseObject(returnContent);
        JSONArray choicesArray = returnContentObject.getJSONArray("choices");
        if (choicesArray != null && !choicesArray.isEmpty()) {
            JSONObject choicesObject = choicesArray.getJSONObject(0);
            if (choicesObject != null) {
                return choicesObject.getString("text");
            }
        }
        return "";
    }


    /**
     * 调用ChatGPTAPI
     *
     * @param question
     * @return
     */
    public String postChatGPT(String question) {

        StringBuffer receive = new StringBuffer();
        BufferedWriter dos = null;
        BufferedReader rd = null;
        HttpURLConnection URLConn = null;

        try {
            // API的地址
            URL url = new URL("https://api.openai.com/v1/completions");
            URLConn = (HttpURLConnection) url.openConnection();
            URLConn.setReadTimeout(1000 * 100);
            URLConn.setConnectTimeout(1000 * 100);
            URLConn.setDoOutput(true);
            URLConn.setDoInput(true);
            URLConn.setRequestMethod("POST");
            URLConn.setUseCaches(false);
            URLConn.setAllowUserInteraction(true);
            URLConn.setInstanceFollowRedirects(true);

            URLConn.setRequestProperty("Content-Type", "application/json");
            // 这里填秘钥，最前面得加"Bearer "
            URLConn.setRequestProperty("Authorization", "Bearer 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");

            JSONObject sendParam = new JSONObject();
            // 语言模型
            sendParam.put("model", "gpt-3.5-turbo-0301");
            // 要问的问题
            //sendParam.put("prompt", question);
            sendParam.put("message","[{\"role\": \"user\", \"content\":"+"\""+question +"\"");
            // 温度，即随机性，0表示随机性最低，2表示随机性最高
            sendParam.put("temperature", 0);
            // 返回最大的字数
            sendParam.put("max_tokens", 3072);

            URLConn.setRequestProperty("Content-Length",
                    String.valueOf(sendParam.toString().getBytes().length));
            dos = new BufferedWriter(new OutputStreamWriter(URLConn.getOutputStream(), "UTF-8"));
            dos.write(sendParam.toString());
            dos.flush();

            rd = new BufferedReader(new InputStreamReader(URLConn.getInputStream(), "UTF-8"));
            String line;
            while ((line = rd.readLine()) != null) {
                receive.append(line);
            }

        } catch (IOException e) {
            receive.append("访问产生了异常-->").append(e.getMessage());
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            URLConn.disconnect();
        }

        String content = receive.toString();
        LOGGER.info("content = " + content);
        return content;
    }
}