package com.towelove.file.controller;


import cn.hutool.http.server.HttpServerResponse;
import com.towelove.file.domain.wechat.ReceiveMessage;
import com.towelove.file.service.WechatService;
import com.towelove.file.util.TokenCheckUtil;
import com.towelove.file.util.XMLUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    @RequestMapping("/")
    public void index(HttpServletResponse response, HttpServletRequest request)throws Exception{
        boolean b = TokenCheckUtil.checkToken(request, token);
        if(b){
            ReceiveMessage receiveMessage = XMLUtil.XMLTOModel(request);
            System.out.println("请求内容如下："+receiveMessage);
            wechatService.orderReply(response,receiveMessage);
        }else{
            System.out.println("请求无效");
        }
    }
}
