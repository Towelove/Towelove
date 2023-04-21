package com.towelove.system.controller.mail;

import com.towelove.common.core.domain.R;
import com.towelove.system.domain.mail.SendLogDo;
import com.towelove.system.service.mail.SendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author 季台星
 * @Date 2023 03 18 11 36
 * 暂时不考虑使用日志功能
 */
@RestController
@RequestMapping("/sys/sendlog/")
public class SendLogController {
    @Autowired
    private SendLogService sendLogService;
    @PostMapping("/create")
    public R createSendLog(@RequestBody SendLogDo sendLogDo){
        if (Objects.isNull(sendLogDo)){
            return R.fail("得到的日志信息为null，无法生成日志");
        }
        Boolean tag = sendLogService.createSendLog(sendLogDo);
        return R.ok(tag);
    }
}
