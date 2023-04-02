package com.towelove.file.controller;

import com.towelove.common.core.utils.StringUtils;
import com.towelove.file.service.WechatService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author: 张锦标
 * @date: 2023/4/2 10:09
 * WechatController
 */
@RestController("/wechat")
public class WechatController {
    @Value("${wechat.mp.token}")
    private String token;
    Logger log = LoggerFactory.getLogger(WechatController.class);

    @GetMapping
    public String returnTokenToWx(){
        return token;
    }

    //@Autowired
    //private WxMpService wxMpService;
    //
    //@GetMapping(produces = "text/plain;charset=utf-8")
    //public String authGet(@RequestParam(name = "signature", required = false) String signature,
    //                      @RequestParam(name = "timestamp", required = false) String timestamp,
    //                      @RequestParam(name = "nonce", required = false) String nonce,
    //                      @RequestParam(name = "echostr", required = false) String echostr) {
    //    if (!this.wxMpService.checkSignature(timestamp, nonce, signature)) {
    //        // 检验失败，返回错误信息
    //        return "非法请求";
    //    }
    //
    //    if (StringUtils.isNotEmpty(echostr)) {
    //        // 如果是服务器配置请求，则返回随机字符串
    //        return echostr;
    //    }
    //
    //    //TODO 处理其他的微信请求，例如消息处理
    //
    //    return "success";
    //}



}