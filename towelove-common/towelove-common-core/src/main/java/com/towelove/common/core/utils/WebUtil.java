package com.towelove.common.core.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: 张锦标
 * @Date: 2023/2/23 20:47
 * Description:
 * Version: 1.0.0
 */
public class WebUtil {
    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string 待渲染的字符串
     */
    public static void renderString(HttpServletResponse response, String string)
    {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
