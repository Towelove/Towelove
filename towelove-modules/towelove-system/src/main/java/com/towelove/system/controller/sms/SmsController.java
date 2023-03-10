package com.towelove.system.controller.sms;

import com.towelove.common.core.domain.R;
import com.towelove.common.security.annotation.RequiresPermissions;
import com.towelove.system.mq.message.sms.SmsSendMessage;
import com.towelove.system.mq.producer.sms.SmsProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: 张锦标
 * @date: 2023/3/8 17:40
 * SmsController类
 */
@RestController
@RequestMapping("/sys/sms")
public class SmsController {
    @Autowired
    private SmsProducer smsProducer;
    @RequiresPermissions("system:sms:send")
    @PostMapping("/send/admin")
    public R<Boolean> sendSmsToAdmin(@RequestBody @Valid SmsSendMessage message){
        smsProducer.sendSmsToAdmin(message);
        return R.ok();
    }

    @PostMapping("/send/user")
    public R<Boolean> sendSmsToUser(@RequestParam("userId")Long userId,
                                    @RequestParam("accountId")Long accountId){
        smsProducer.sendSmsToUser(userId,accountId);
        return R.ok();
    }

}
