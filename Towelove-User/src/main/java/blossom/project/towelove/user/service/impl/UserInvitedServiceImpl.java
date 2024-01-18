package blossom.project.towelove.user.service.impl;

import blossom.project.towelove.common.constant.TokenConstant;
import blossom.project.towelove.common.constant.UserConstants;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.user.service.UserInvitedService;
import com.alibaba.nacos.common.http.handler.RequestHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.service.impl
 * @className: UserInvitedServiceImpl
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/18 22:38
 * @version: 1.0
 */
@Service
public class UserInvitedServiceImpl implements UserInvitedService {
    @Override
    public Result invited(UserInvitedService userInvitedService) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String userId = requestAttributes.getRequest().getHeader(TokenConstant.USER_ID_HEADER);
        //将userId转换为32进制
        int invitedCode = Integer.parseInt(userId, 36);
        //发送模板信息
        return null;
    }
}
