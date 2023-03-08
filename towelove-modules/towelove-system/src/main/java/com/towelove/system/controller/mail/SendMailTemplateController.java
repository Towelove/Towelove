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
 */
@RestController
@RequestMapping("/sys/send/mail/template")
public class SendMailTemplateController {
    @Autowired
    private MailSendService mailSendService;

    @GetMapping("/user")
    public R<Long> sendSingleMailToMember(@RequestBody MailSendSingleToUserReqDTO reqDTO) {
        return R.ok(mailSendService.sendSingleMailToUser(
                reqDTO.getMail(), reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams()));
    }
    @GetMapping("/admin")
    public R<Long> sendSingleMailToAdmin(@RequestBody MailSendSingleToUserReqDTO reqDTO) {
        return R.ok(mailSendService.sendSingleMailToUser(
                reqDTO.getMail(), reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams()));
    }

}
