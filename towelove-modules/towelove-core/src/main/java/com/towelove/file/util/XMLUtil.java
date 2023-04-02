package com.towelove.file.util;

import com.towelove.file.domain.wechat.ReceiveMessage;
import com.towelove.file.domain.wechat.ReplyMessage;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.StringWriter;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
/**
 * @author: 张锦标
 * @date: 2023/4/2 15:13
 * XMLUtil类
 */
public class XMLUtil {
    public static ReceiveMessage XMLTOModel(HttpServletRequest request) {
        ReceiveMessage receiveMessage = new ReceiveMessage();
        try {
            InputStream inputstream;
            StringBuffer sb = new StringBuffer();
            inputstream = request.getInputStream();
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
            Document document = DocumentHelper.parseText(String.valueOf(sb));
            Element root = document.getRootElement();
            receiveMessage.setToUserName(root.elementText("ToUserName"));
            receiveMessage.setFromUserName(root.elementText("FromUserName"));
            receiveMessage.setMsgType(root.elementText("MsgType"));
            receiveMessage.setContent(root.elementText("Content"));
            receiveMessage.setCreateTime(root.elementText("CreateTime"));
            receiveMessage.setMsgId(root.elementText("MsgId"));
            receiveMessage.setMsgDataId(root.elementText("MsgDataId"));
            receiveMessage.setIdx(root.elementText("Idx"));
            //关注
            receiveMessage.setEvent(root.elementText("Event"));
            in.close();
            inputstream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return receiveMessage;
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
