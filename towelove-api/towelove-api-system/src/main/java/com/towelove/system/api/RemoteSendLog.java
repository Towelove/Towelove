package com.towelove.system.api;

import com.towelove.common.core.domain.R;
import com.towelove.system.api.model.SendLogDo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

/**
 * @author 季台星
 * @Date 2023 03 18 11 49
 */
@FeignClient(value = "towelove-system" , contextId = "RemoteSendLog")
public interface RemoteSendLog {
    @PostMapping("/sys/sendlog/create")
    R createSendLog(@RequestBody SendLogDo sendLogDo);
}
