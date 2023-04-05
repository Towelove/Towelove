package com.towelove.file.util;

import com.towelove.file.domain.wx.ReceiveMessage;
import com.towelove.file.domain.wx.ReplyMessage;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.StringWriter;

import org.dom4j.io.XMLWriter;
/**
 * @author: 张锦标
 * @date: 2023/4/2 15:13
 * XMLUtil类
 */
public class XMLUtil {
    public static void main(String[] args) {
        String str = "<xml><ToUserName><![CDATA[gh_71a0837d69a6]]></ToUserName>\n" +
                "<FromUserName><![CDATA[oy__X6JbTiLxEVG85ABtAawsc_qw]]></FromUserName>\n" +
                "<CreateTime>1680571837</CreateTime>\n" +
                "<MsgType><![CDATA[text]]></MsgType>\n" +
                "<Content><![CDATA[张锦标请加油！]]></Content>\n" +
                "<MsgId>24060005236946394</MsgId>\n" +
                "</xml>";
        System.out.println(XMLUtil.parseXmlToMsg(str));
    }
    public  static ReceiveMessage parseXmlToMsg(String str) {
        ReceiveMessage receiveMessage = new ReceiveMessage();
        try {
            Document document = DocumentHelper.parseText(String.valueOf(str));
            Element root = document.getRootElement();
            receiveMessage.setToUserName(root.elementText("FromUserName"));
            receiveMessage.setFromUserName(root.elementText("ToUserName"));
            receiveMessage.setMsgType(root.elementText("MsgType"));
            receiveMessage.setContent(root.elementText("Content"));
            receiveMessage.setCreateTime(root.elementText("CreateTime"));
            receiveMessage.setMsgId(root.elementText("MsgId"));
            //receiveMessage.setMsgDataId(root.elementText("MsgDataId"));
            //receiveMessage.setIdx(root.elementText("Idx"));
            ////关注
            //receiveMessage.setEvent(root.elementText("Event"));
        } catch (Exception e) {
            System.out.println(e);
        }
        return receiveMessage;
    }
    public static  String parseMsgToXML(ReceiveMessage receiveMessage) {
        // 创建document对象
        Document document = DocumentHelper.createDocument();
        // 创建根节点bookRoot
        Element xml = document.addElement("xml");
        // 向根节点中添加第一个节点
        Element toUserName = xml.addElement("ToUserName");
        // 向子节点中添加属性
        toUserName.addCDATA(receiveMessage.getToUserName());
        Element fromUserName = xml.addElement("FromUserName");
        fromUserName.addCDATA(receiveMessage.getFromUserName());
        Element createTime = xml.addElement("CreateTime");
        createTime.addCDATA(String.valueOf(System.currentTimeMillis()));
        Element msgType = xml.addElement("MsgType");
        msgType.addCDATA(receiveMessage.getMsgType());
        Element content = xml.addElement("Content");
        //这里的content内容应该是你从数据库或者某种方式生成的 而不是固定的
        content.addCDATA("hello呀，我是张锦标");
        String asXML = document.getRootElement().asXML();
        System.out.println(asXML);
        return asXML;

    }
    public static String ObjToXml(ReplyMessage obj) throws Exception {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(obj.getClass().getSimpleName());
        convertObjectToXml(obj, root);
        StringWriter stringWriter = new StringWriter();
        XMLWriter writer = new XMLWriter(stringWriter);
        writer.write(document);
        writer.close();
        return stringWriter.toString();
    }
    private static void convertObjectToXml(Object obj, Element element) throws Exception {
        Class<?> clazz = obj.getClass();
        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Element child = element.addElement(field.getName());
            Object value = field.get(obj);
            if (value != null) {
                if (value.getClass().isPrimitive() || value.getClass() == java.lang.String.class) {
                    child.setText(value.toString());
                } else {
                    convertObjectToXml(value, child);
                }
            }
        }
    }
}
