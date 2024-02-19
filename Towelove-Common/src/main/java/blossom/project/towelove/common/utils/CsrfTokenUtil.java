package blossom.project.towelove.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author: ZhangBlossom
 * @date: 2024/1/16 10:33
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 * 创建一个工具类来生成和验证CSRF令牌。
 */
public class CsrfTokenUtil {

    private static final String CSRF_TOKEN_SESSION_ATTR = "CSRF_TOKEN";

    /**
     * 为某个session生成一个特定的token
     * @param session
     * @return
     */
    public static String createToken(HttpSession session) {
        String token = UUID.randomUUID().toString();
        session.setAttribute(CSRF_TOKEN_SESSION_ATTR, token);
        return token;
    }

    /**
     * 检测当前会话中的token和请求体携带的成熟token是否一致
     * 如果不一致，说明不是同一个会话，返回false
     * @param request
     * @return
     */
    public static boolean validateToken(HttpServletRequest request) {
        String sessionToken = (String) request.getSession().getAttribute(CSRF_TOKEN_SESSION_ATTR);
        String requestToken = request.getParameter("csrfToken");
        return sessionToken != null && sessionToken.equals(requestToken);
    }
}
