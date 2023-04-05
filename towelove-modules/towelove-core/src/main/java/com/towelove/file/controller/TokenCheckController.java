package com.towelove.file.controller;


import com.alibaba.fastjson.JSONObject;
import com.towelove.common.core.constant.WxConstant;
import com.towelove.common.core.domain.R;
import com.towelove.file.domain.wx.ReceiveMessage;
import com.towelove.file.domain.wx.Text;
import com.towelove.file.domain.wx.WxCustomMessage;
import com.towelove.file.service.WechatService;
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
import java.util.concurrent.ThreadPoolExecutor;
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
    @Autowired
    private WechatService wechatService;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    public static final String path = "https://openapi.capoo.cc/v1/completions"; //无需代理
    //public static final String path = "https://openapi.deno.capoo.cc/v1/completions";
    //public static final String path = "https://openapi.cf.capoo.cc/v1/completions";
    //public static final String path = "https://api.openai.com/v1/completions"; //必须代理
    @Value("${wechat.mp.token}")
    private String token;
    @Value("${wechat.mp.appId}")
    private String appId;
    @Value("${wechat.mp.secret}")
    private String secret;
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

        //异步调用客服接口，只需要在48小时内给出响应即可
        //threadPoolExecutor.execute(() -> {
        //    //得到用户的OpenId
        //    getCustomResponse(receiveMessage.getFromUserName(),
        //            receiveMessage.getContent());
        //});

        return XMLUtil.parseMsgToXML(receiveMessage);
    }

    @GetMapping("/customResponse")
    public R getCustomResponse(String openId, String content) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(300, TimeUnit.SECONDS).writeTimeout(100
                , TimeUnit.SECONDS).readTimeout(100, TimeUnit.SECONDS).callTimeout(100, TimeUnit.SECONDS).build();
        //替换url中的token,远程调用微信的接口得到accessToken
        String url = WxConstant.Send_Custom_Message.replace("ACCESS_TOKEN", wechatService.getAccessToken(appId,
                secret));
        ;
        /**
         * 设置返回类型为json
         */
        MediaType mediaType = MediaType.parse("application/json");
        WxCustomMessage wxCustomMessage = new WxCustomMessage();
        //得到openId
        wxCustomMessage.setTouser(openId);
        wxCustomMessage.setMsgtype("text");
        Text text = new Text();
        text.setContent(wechatService.getContentFromGpt(content));
        wxCustomMessage.setText(text);
        //将请求体设置为JSON
        String requestBody = JSONObject.toJSONString(wxCustomMessage);
        //构建请求
        Request request =
                new Request.Builder().url(url).post(okhttp3.RequestBody.create(mediaType, requestBody)).build();
        //发起请求
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                log.info("POST请求得到的响应体: response======>" + response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.ok();
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
