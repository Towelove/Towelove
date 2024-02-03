package blossom.project.towelove.user.service.impl;

import blossom.project.towelove.client.serivce.msg.RemoteEmailService;
import blossom.project.towelove.client.serivce.user.RemoteUserService;
import blossom.project.towelove.common.constant.StringTemplateConstants;
import blossom.project.towelove.common.request.user.InvitedEmailRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.SysUserVo;
import blossom.project.towelove.user.entity.SysUser;
import blossom.project.towelove.user.interceptor.UserInfoContextHolder;
import blossom.project.towelove.user.service.UserInvitedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    @Autowired
    private RemoteEmailService emailService;

    @Autowired
    private RemoteUserService userService;

    @Override
    public Result invited(UserInvitedService userInvitedService) {
        SysUser userInfo = UserInfoContextHolder.getUserInfo();
        //将userId转换为32进制
        Long userId = userInfo.getId();
        Result<SysUserVo> beInvitedUser = userService.getUserById(userId);
        if (Objects.isNull(beInvitedUser) || Objects.isNull(beInvitedUser.getData())) {
            return Result.fail("当前ID对应用户不存在,用户ID为：" + userId);
        }
        String invitedCode = Long.toString(userId, 36);
        String invitedTemplate = StringTemplateConstants.getInvitedTemplate(userInfo.getNickName(), "https://www" +
                ".baidu.com?invitedCode=" + invitedCode);
        log.info("用户：{}，发送邀请：{}", userId, invitedTemplate);
        //TODO 编辑一下要发送到对方邮箱的内容
        InvitedEmailRequest request =
                InvitedEmailRequest.builder().email(beInvitedUser.getData().getEmail())
                        .content("xxx邀请你绑定情侣关系拉...").build();
        emailService.sendInvitedEmail(request);
        //发送模板信息
        return Result.ok(invitedTemplate);
    }
}
