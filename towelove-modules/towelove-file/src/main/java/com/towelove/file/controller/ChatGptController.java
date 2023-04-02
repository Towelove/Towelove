package com.towelove.file.controller;

import com.towelove.common.core.domain.R;
import com.towelove.file.service.impl.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    /**
     * 获取chatGPT返回的数据
     * @param question
     * @return
     */
    @PostMapping("/chatGPT")
    public R<String> chatGPT(@RequestParam String question) {
        String result = chatGptService.getResultFromChatGPT(question);
        return R.ok(result);
    }


}
