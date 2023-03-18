package com.towelove.system.service.mail;

import com.towelove.system.domain.mail.SendLogDo;
import com.towelove.system.mapper.mail.SendLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author 季台星
 * @Date 2023 03 18 11 36
 */
@Service
@Validated
@Slf4j
public class SendLogServiceImpl implements SendLogService{
    @Autowired
    private SendLogMapper sendLogMapper;


    @Override
    public boolean createSendLog(SendLogDo sendLogDo) {
        int insert = sendLogMapper.insert(sendLogDo);
        return insert > 0 ? true : false;
    }
}
