package com.towelove.system.controller.mail;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.towelove.common.core.domain.R;
import com.towelove.system.domain.mail.dto.MailSendSingleToUserReqDTO;
import com.towelove.system.domain.mail.vo.template.MailTemplateSendReqVO;
import com.towelove.system.service.mail.MailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 张锦标
 * @date: 2023/3/8 10:28
 * SendMailController类
 * 当前类用于管理员发送邮件给管理员或者普通用户
 */
@RestController
@RequestMapping("/sys/send/mail/template")
public class SendMailTemplateController {
    @Autowired
    private MailSendService mailSendService;

    /**
     * 发送邮件给用户
     * @param reqDTO 接收邮件的用户信息
     * @return
     */
    @GetMapping("/user")
    public R<Long> sendSingleMailToUser(@RequestBody MailSendSingleToUserReqDTO reqDTO) {
        return R.ok(mailSendService.sendSingleMailToUser(
                reqDTO.getMail(), reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams()));
    }

    /**
     * 发送邮件给管理员
     * @param reqDTO 接收邮件的管理员信息
     * @return
     */
    @GetMapping("/admin")
    public R<Long> sendSingleMailToAdmin(@RequestBody MailSendSingleToUserReqDTO reqDTO) {
        return R.ok(mailSendService.sendSingleMailToAdmin(
                reqDTO.getMail(), reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams()));
    }

}
