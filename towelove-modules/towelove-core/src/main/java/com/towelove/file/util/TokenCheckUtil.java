package com.towelove.file.util;

import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static cn.hutool.crypto.SecureUtil.sha1;

/**
 * @author: 张锦标
 * @date: 2023/4/2 15:10
 * TokenCheckUtil类
 */
public class TokenCheckUtil {
    public static String checkToken(HttpServletRequest request, String token) throws NoSuchAlgorithmException {
        String method = request.getMethod();
        //微信token验证关po5t请求
        if ("GET".equals(method)) {
            //微信加密签名
            String echostr = request.getParameter("echostr");//时阅鲛
            String signature = request.getParameter("signature");//随机宁符串
            String timestamp = request.getParameter("timestamp");//随机数
            String nonce = request.getParameter("nonce");
            String[] str = {token, timestamp, nonce};
            //字典排序
            Arrays.sort(str);
            String bigStr = str[0] + str[1] + str[2];// SHA1加密
            String digest = sha1(bigStr);//对比签名
            if (digest.equals(signature)) {
                return echostr;
            } else {
                return "";
            }
        }
        return "";
    }
}
