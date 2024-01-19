package blossom.project.towelove.user.service.impl;

import blossom.project.towelove.common.constant.StringTemplateConstants;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.user.entity.SysUser;
import blossom.project.towelove.user.interceptor.UserInfoContextHolder;
import blossom.project.towelove.user.service.UserInvitedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
@Slf4j
public class UserInvitedServiceImpl implements UserInvitedService {
    @Override
    public Result invited(UserInvitedService userInvitedService) {
        SysUser userInfo = UserInfoContextHolder.getUserInfo();
        //将userId转换为32进制
        Long userId = userInfo.getId();
        String invitedCode = Long.toString(userId, 36);
        String invitedTemplate = StringTemplateConstants.getInvitedTemplate(userInfo.getNickName(), "https://www.baidu.com?invitedCode=" + invitedCode);
        //TODO:调用邮件服务发送邮件
        log.info("用户：{}，发送邀请：{}",userId,invitedTemplate);
        //发送模板信息
        return Result.ok(invitedTemplate);
    }
}
