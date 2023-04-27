package com.towelove.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.towelove.auth.form.UserInfo;
import com.towelove.auth.service.UserInfoService;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.utils.ConstantWxPropertiesUtils;
import com.towelove.common.core.utils.HttpClientUtils;
import com.towelove.common.core.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 张锦标
 * @date: 2023/4/27 10:50
 * WeixinApiController类
 */
@RequestMapping("/wx/api")
public class WeixinApiController {
    @Autowired
    private UserInfoService userInfoService;


    //微信扫描后回调的方法
    @GetMapping("callback")
    public String callback(String code,String state) {
        //第一步 获取临时票据 code
        System.out.println("code:"+code);
        //第二步 拿着code和微信id和秘钥，请求微信固定地址 ，得到两个值
        //使用code和appid以及appscrect换取access_token
        //  %s   占位符
        StringBuffer baseAccessTokenUrl = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");
        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
                ConstantWxPropertiesUtils.WX_OPEN_APP_ID,
                ConstantWxPropertiesUtils.WX_OPEN_APP_SECRET,
                code);
        //使用httpclient请求这个地址
        try {
            String accesstokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accesstokenInfo:"+accesstokenInfo);
            //从返回字符串获取两个值 openid  和  access_token
            JSONObject jsonObject = JSONObject.parseObject(accesstokenInfo);
            String access_token = jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");

            //判断数据库是否存在微信的扫描人信息
            //根据openid判断
            UserInfo userInfo = userInfoService.selectWxInfoOpenId(openid);
            if(userInfo == null) { //数据库不存在微信信息
                //第三步 拿着openid  和  access_token请求微信地址，得到扫描人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                String resultInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultInfo:"+resultInfo);
                JSONObject resultUserInfoJson = JSONObject.parseObject(resultInfo);
                //解析用户信息
                //用户昵称
                String nickname = resultUserInfoJson.getString("nickname");
                //用户头像
                String headimgurl = resultUserInfoJson.getString("headimgurl");

                //获取扫描人信息添加数据库
                userInfo = new UserInfo();
                userInfo.setNickName(nickname);
                userInfo.setOpenid(openid);
                userInfo.setStatus(1);
                userInfoService.save(userInfo);
            }
            //返回name和token字符串
            Map<String,Object> map = new HashMap<>();
            String name = userInfo.getName();
            if(StringUtils.isEmpty(name)) {
                name = userInfo.getNickName();
            }
            if(StringUtils.isEmpty(name)) {
                name = userInfo.getPhone();
            }
            map.put("name", name);

            //判断userInfo是否有手机号，如果手机号为空，返回openid
            //如果手机号不为空，返回openid值是空字符串
            //前端判断：如果openid不为空，绑定手机号，如果openid为空，不需要绑定手机号
            if(StringUtils.isEmpty(userInfo.getPhone())) {
                map.put("openid", userInfo.getOpenid());
            } else {
                map.put("openid", "");
            }
            //使用jwt生成token字符串
            map.put("id",userInfo.getId());
            String token = JwtUtils.createToken(map);
            map.put("token", token);
            //跳转到前端页面
            return "redirect:" + ConstantWxPropertiesUtils.TOWELOVE_BASE_URL + "/weixin/callback?token="+map.get("token")+
                    "&openid="+map.get("openid")+"&name="+
                    URLEncoder.encode((String) map.get("name"),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取微信登录参数
     */
    @GetMapping("getLoginParam")
    @ResponseBody
    @ApiOperation(value = "获取微信登录参数")
    public R genQrConnect(HttpSession session) throws UnsupportedEncodingException {
        String redirectUri = URLEncoder.encode(ConstantWxPropertiesUtils.WX_OPEN_REDIRECT_URL, "UTF-8");
        System.out.println("微信二维码的URL是："+redirectUri);
        Map<String, Object> map = new HashMap<>();
        map.put("appid", ConstantWxPropertiesUtils.WX_OPEN_APP_ID);
        map.put("redirectUri", redirectUri);
        map.put("scope", "snsapi_login");
        map.put("state", System.currentTimeMillis()+"");//System.currentTimeMillis()+""
        return R.ok(map);
    }
}
