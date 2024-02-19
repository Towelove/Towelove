package blossom.project.towelove.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
/**
 * @author: ZhangBlossom
 * @date: 2024/1/16 10:30
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
public class XssUtil {

    /**
     * 当前方法用于清洗用户输入的字符串
     * 解决XSS导致的js攻击
     * @param input 可能存在问题的input字符串
     * @return
     */
    public static String sanitize(String input) {
        return Jsoup.clean(input, Whitelist.none());
    }
}
