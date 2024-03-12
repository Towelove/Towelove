package blossom.project.towelove.framework.user.core;

import blossom.project.towelove.common.constant.TokenConstant;
import cn.hutool.core.util.StrUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.framework.log.core
 * @className: UserInfoFilter
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/22 14:20
 * @version: 1.0
 */
public class UserInfoFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //从请求头中获取用户信息
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String userId = request.getHeader(TokenConstant.USER_ID_HEADER);
        if (StrUtil.isNotBlank(userId)){
            //存在用户信息
            String nickName = request.getHeader(TokenConstant.USER_NICK_HEADER);
            String userName = request.getHeader(TokenConstant.USER_NAME_HEADER);
            String sex = request.getHeader(TokenConstant.USER_SEX);
            String token = request.getHeader(TokenConstant.USER_TOKEN);
            String coupleId = request.getHeader(TokenConstant.USER_COUPLE_ID);
            String email = request.getHeader(TokenConstant.USER_EMAIL);
            String phone = request.getHeader(TokenConstant.USER_PHONE);
            //用户名与昵称可能为中文 所以需要转化为UTF8编码/解码
            if (StrUtil.isNotBlank(nickName)){
                nickName = URLDecoder.decode(nickName, StandardCharsets.UTF_8);
            }
            if (StrUtil.isNotBlank(userName)){
                userName = URLDecoder.decode(userName,StandardCharsets.UTF_8);
            }
            if (StrUtil.isNotBlank(sex)){
                sex = URLDecoder.decode(sex,StandardCharsets.UTF_8);
            }
            //构建UserInfoDTO
            UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                    .id(Long.valueOf(userId))
                    .nickName(nickName)
                    .userName(userName)
                    .sex(sex)
                    .token(token)
                    .phone(phone)
                    .email(email)
                    .build();
            if (StrUtil.isNotBlank(coupleId)) userInfoDTO.setCoupleId(Long.valueOf(coupleId));
            UserInfoContextHolder.set(userInfoDTO);
        }
        try {
            filterChain.doFilter(servletRequest,servletResponse);
        }finally {
            //删除ThreadLocal中的信息
            UserInfoContextHolder.clean();
        }
    }

    @Override
    public void destroy() {
        //删除threadLocal中的信息
        UserInfoContextHolder.clean();
        Filter.super.destroy();
    }
}
