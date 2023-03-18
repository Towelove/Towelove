package com.towelove.system.service.mail;

import com.baomidou.mybatisplus.extension.service.IService;
import com.towelove.system.domain.mail.SendLogDo;

/**
 * @author 季台星
 * @Date 2023 03 18 11 35
 */
public interface SendLogService {

    boolean createSendLog(SendLogDo sendLogDo);
}
